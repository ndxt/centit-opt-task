package com.centit.task.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.centit.support.common.ObjectException;
import com.centit.support.database.utils.PageDesc;
import com.centit.task.dao.TaskLogDao;
import com.centit.task.po.TaskLog;
import com.centit.task.service.TaskInfoService;
import com.centit.task.service.TaskLogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * @author liu_cc
 * @create 2021-07-01 17:37
 */
@Service
@Slf4j
public class TaskLogServiceImpl implements TaskLogService {

    @Autowired
    private TaskLogDao taskLogDao;

    @Autowired
    private TaskInfoService taskInfoService;

    @Override
    @Transactional
    public List<TaskLog> listTaskLogs(Map<String, Object> filterMap, PageDesc pageDesc) {
        return taskLogDao.listObjectsByProperties(filterMap, pageDesc);
    }

    @Override
    @Transactional
    public TaskLog getTaskLogByCode(String logId) {
        return taskLogDao.getObjectById(logId);
    }

    @Override
    @Transactional
    public void saveTaskLog(TaskLog taskLog) {
        taskLogDao.mergeObject(taskLog);
        if (0!=taskLog.getWorkload() && !"M".equals(taskLog.getLogType())){
            taskInfoService.incrementWorkload(taskLog.getWorkload(),taskLog.getTaskId());
        }
    }

    @Override
    @Transactional
    public void deleteTaskLogByCode(String logId,String userCode) {
        //校验只能删除本人创建的日志
        TaskLog taskLog = taskLogDao.getObjectById(logId);
        if (null == taskLog){
            throw new ObjectException("日志信息不存在!");
        }
        if (!taskLog.getLogType().equals("R") || !taskLog.getUserCode().equals(userCode)){
            throw new ObjectException("只能删除自己的日志信息");
        }
        taskInfoService.decrementWorkload(taskLog.getWorkload(),taskLog.getTaskId());
        taskLogDao.deleteObjectById(logId);
    }

    @Override
    public JSONArray statTaskLog(Map<String, Object> filterMap) {
        return taskLogDao.statTaskLog(filterMap);
    }

}
