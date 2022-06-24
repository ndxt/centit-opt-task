package com.centit.task.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.centit.framework.components.CodeRepositoryUtil;
import com.centit.framework.model.basedata.IDataDictionary;
import com.centit.framework.model.basedata.IUserInfo;
import com.centit.support.algorithm.CollectionsOpt;
import com.centit.support.common.ObjectException;
import com.centit.support.database.utils.PageDesc;
import com.centit.task.dao.TaskInfoDao;
import com.centit.task.dao.TaskLogDao;
import com.centit.task.po.TaskInfo;
import com.centit.task.po.TaskLog;
import com.centit.task.service.TaskInfoService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

/**
 * @author liu_cc
 * @create 2021-07-01 17:37
 */
@Service
@Slf4j
public class TaskInfoServiceImpl implements TaskInfoService {

    @Autowired
    private TaskInfoDao taskInfoDao;

    @Autowired
    private TaskLogDao taskLogDao;

    /**
     * 修改任务状态日志模板
     */
    private final String TASK_STATE_TEMPLATE = "%s更改任务状态为%s。";

    /**
     * 任务转移日志模板
     */
    private final String TASK_TRANSFER_TEMPLATE = "任务由%s从%s转移到%s。";

    /**
     * 任务创建日志模板
     */
    private final String TASK_CREATE_TEMPLATE = "%s创建了任务，并且把任务分配给了%s。";

    /**
     * 修改任务标题日志模板
     */
    private final String TASK_TITLE_UPDATE_TEMPLATE = "%s把任务标题由%s修改为%s。";

    /**
     * 修改任务内容日志模板
     */
    private final String TASK_CONTENT_UPDATE_TEMPLATE = "%s修改了任务详情。";

    @Override
    @Transactional
    public List<TaskInfo> listTaskInfos(Map<String, Object> filterMap, PageDesc pageDesc) {
        return taskInfoDao.listObjects(filterMap, pageDesc);
    }

    @Override
    @Transactional
    public TaskInfo getTaskInfoByCode(String taskId) {
        return taskInfoDao.getObjectById(taskId);
    }

    @Override
    @Transactional
    public void saveTaskInfo(TaskInfo taskInfo) {
        IUserInfo taskOfficerInfo = CodeRepositoryUtil.getUserInfoByCode(taskInfo.getUnitCode(), taskInfo.getTaskOfficer());
        if (null == taskOfficerInfo){
            throw new ObjectException("任务分配人信息有误");
        }
        taskInfo.setWorkload(0L);
        taskInfoDao.mergeObject(taskInfo);
        IUserInfo reporterNameInfo = CodeRepositoryUtil.getUserInfoByCode(taskInfo.getUnitCode(), taskInfo.getTaskReporter());
        updateMemoTaskLog(taskInfo,String.format(TASK_CREATE_TEMPLATE,reporterNameInfo.getUserName(),taskOfficerInfo.getUserName()));
    }

    @Override
    @Transactional
    public void deleteTaskInfoByCode(String taskId,String userCode) {
        TaskInfo dbTaskInfo = taskInfoDao.getObjectById(taskId);
        if (!dbTaskInfo.getTaskReporter().equals(userCode)){
            throw new ObjectException("只有任务报告人才能删除任务");
        }
        //删除任务及相关日志信息
        taskLogDao.deleteObjectsByProperties(CollectionsOpt.createHashMap("taskId",dbTaskInfo.getTaskId()));
        taskInfoDao.deleteObjectById(taskId);
    }

    @Override
    @Transactional
    public void updateTaskInfo(TaskInfo taskInfo) {
        TaskInfo dbTaskInfo = taskInfoDao.getObjectById(taskInfo.getTaskId());
        if (null == dbTaskInfo) {
            throw new ObjectException("任务不存在!");
        }
        taskInfoDao.updateObject(taskInfo);

        appendSystemLog(taskInfo, dbTaskInfo);
    }

    /**
     * WORKLOAD字段自增increment
     *
     * @param increment 增加数量
     * @param taskId     任务id
     */
    @Override
    public void incrementWorkload(long increment, String taskId) {
        taskInfoDao.incrementWorkload(increment, taskId);
    }

    /**
     * WORKLOAD字段自减decrement
     * @param increment 减少属性
     * @param taskId 任务id
     */
    @Override
    public void decrementWorkload(long increment, String taskId) {
        taskInfoDao.decrementWorkload(increment, taskId);
    }
    /**
     * 当supplier.get()未空或者不等于oldValue时 值为true，代表已改变
     *
     * @param supplier 提供者
     * @param oldValue 原本的值
     * @return
     */
    private boolean isChange(Supplier<String> supplier, String oldValue) {
        return StringUtils.isNotBlank(supplier.get()) && !supplier.get().equals(oldValue);
    }


    /**
     * 根据TaskInfo更新日志
     * @param taskInfo 任务基本信息
     * @param logContent 日志内容
     */
    private void updateMemoTaskLog(TaskInfo taskInfo, String logContent ) {
        TaskLog taskLog = new TaskLog();
        taskLog.setTaskId(taskInfo.getTaskId());
        taskLog.setLogType("M");
        //系统日志创建人设置为 system
        taskLog.setUserCode("system");
        taskLog.setUnitCode(taskInfo.getUnitCode());
        taskLog.setWorkload(0L);
        taskLog.setLogContent(logContent);
        taskLog.setOsId(taskInfo.getOsId());
        taskLogDao.saveNewObject(taskLog);
    }

    /**
     *taskInfo属性改变后，追加对应的系统日志
     * @param taskInfo 新的任务详情
     * @param dbTaskInfo 原本的任务详情
     */
    private void appendSystemLog(TaskInfo taskInfo, TaskInfo dbTaskInfo) {
        if (isChange(taskInfo::getTaskState, dbTaskInfo.getTaskState())) {
            //任务状态发生改变
            IUserInfo currentUserInfo = CodeRepositoryUtil.getUserInfoByCode(taskInfo.getUnitCode(), taskInfo.getUserCode());
            if (null == currentUserInfo){
                throw new ObjectException("当前用户信息有误");
            }
            IDataDictionary taskStateDic = CodeRepositoryUtil.getDataPiece("taskState", taskInfo.getTaskState(), null);
            String taskStateText = null == taskStateDic ? taskInfo.getTaskState() : taskStateDic.getDataValue();
            updateMemoTaskLog(taskInfo, String.format(TASK_STATE_TEMPLATE,currentUserInfo.getUserName(),taskStateText));
        }
        if (isChange(taskInfo::getTaskOfficer, dbTaskInfo.getTaskOfficer())) {
            //任务分配人发生改变
            IUserInfo taskOfficerUserInfo = CodeRepositoryUtil.getUserInfoByCode(taskInfo.getUnitCode(), taskInfo.getTaskOfficer());
            if (null == taskOfficerUserInfo) {
                throw new ObjectException(taskInfo.getTaskOfficer() + " 用户不存在!");
            }
            IUserInfo dbUserInfo = CodeRepositoryUtil.getUserInfoByCode(dbTaskInfo.getUnitCode(), dbTaskInfo.getTaskOfficer());
            IUserInfo taskInfoUserInfo = CodeRepositoryUtil.getUserInfoByCode(taskInfo.getUnitCode(), taskInfo.getUserCode());
            String logContent = String.format(TASK_TRANSFER_TEMPLATE, taskInfoUserInfo.getUserName(), dbUserInfo.getUserName(), taskOfficerUserInfo.getUserName());
            updateMemoTaskLog(taskInfo,logContent);
        }

        if (isChange(taskInfo::getTaskTitle,dbTaskInfo.getTaskTitle())){
            //任务标题发生改变
            IUserInfo currentUserInfo = CodeRepositoryUtil.getUserInfoByCode(taskInfo.getUnitCode(), taskInfo.getUserCode());
            if (null == currentUserInfo) {
                throw new ObjectException(taskInfo.getTaskOfficer() + " 用户不存在!");
            }
            String logContent = String.format(TASK_TITLE_UPDATE_TEMPLATE, currentUserInfo.getUserName(),dbTaskInfo.getTaskTitle() ,taskInfo.getTaskTitle());
            updateMemoTaskLog(taskInfo,logContent);
        }
        if (isChange(taskInfo::getTaskContent,dbTaskInfo.getTaskContent())){
            //内容发生改变
            IUserInfo currentUserInfo = CodeRepositoryUtil.getUserInfoByCode(taskInfo.getUnitCode(), taskInfo.getUserCode());
            if (null == currentUserInfo) {
                throw new ObjectException(taskInfo.getTaskOfficer() + " 用户不存在!");
            }
            updateMemoTaskLog(taskInfo,String.format(TASK_CONTENT_UPDATE_TEMPLATE, currentUserInfo.getUserName()));
        }
    }
    @Override
    public JSONArray statTaskInfo(Map<String,Object> filterMap){
        return taskInfoDao.statTaskInfo(filterMap);
    }
}
