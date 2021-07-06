package com.centit.task.service.impl;

import com.centit.framework.core.dao.CodeBook;
import com.centit.support.common.ObjectException;
import com.centit.support.database.utils.PageDesc;
import com.centit.task.dao.RoleDeputeDao;
import com.centit.task.dao.TaskTransferDao;
import com.centit.task.dao.UserTaskDao;
import com.centit.task.po.RoleDepute;
import com.centit.task.po.TaskTransfer;
import com.centit.task.po.UserTask;
import com.centit.task.service.UserTaskManager;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * @author liu_cc
 * @create 2021-04-23 14:35
 */
@Service
@Slf4j
public class UserTaskManagerImpl implements UserTaskManager {
    private static final long serialVersionUID = 1L;

    @Autowired
    private UserTaskDao userTaskDao;

    @Autowired
    private RoleDeputeDao roleDeputeDao;

    @Autowired
    private TaskTransferDao transferDao;

    @Override
    public String saveUserTask(UserTask userTask) {
        userTask.setAssignTime(new Date());
        userTaskDao.mergeObject(userTask);
        return userTask.getTaskId();
    }

    @Override
    public List<String> saveUserTaskList(List<UserTask> userTaskList) {
        if (userTaskList == null || userTaskList.isEmpty()) {
            return null;
        }
        List<String> list = new ArrayList<>();
        for (UserTask userTask : userTaskList) {
            list.add(this.saveUserTask(userTask));
        }
        return list;
    }

    @Override
    public List<UserTask> listUserTask(String userCode, int offset, int maxsize) {
        return userTaskDao.listUserTask(userCode, offset, maxsize);
    }

    @Override
    public List<UserTask> listUserTask(Map<String, Object> filterMap, PageDesc pageDesc) {
        filterMap.put("taskState", UserTask.TASK_STATE_ALLOCATED);
        filterMap.put(CodeBook.SELF_ORDER_BY, "assignTime desc");
        return userTaskDao.listObjects(filterMap, pageDesc);
    }

    @Override
    public List<UserTask> listUserTask(Map<String, Object> filterMap, int offset, int maxsize) {
        filterMap.put("taskState", UserTask.TASK_STATE_ALLOCATED);
        filterMap.put(CodeBook.SELF_ORDER_BY, "assignTime desc");
        return userTaskDao.listObjectsByProperties(filterMap, offset, maxsize);
    }

    @Override
    public List<UserTask> listUserCompleteTask(String userCode, int offset, int maxsize) {
        return userTaskDao.listUserCompleteTask(userCode, offset, maxsize);
    }

    @Override
    public List<UserTask> listUserCompleteTask(Map<String, Object> filterMap, PageDesc pageDesc) {
        filterMap.put("taskState", UserTask.TASK_STATE_COMPLETED);
        filterMap.put(CodeBook.SELF_ORDER_BY, "assignTime desc");
        return userTaskDao.listObjects(filterMap, pageDesc);
    }

    @Override
    public List<UserTask> listUserCompleteTask(Map<String, Object> filterMap, int offset, int maxsize) {
        filterMap.put("taskState", UserTask.TASK_STATE_COMPLETED);
        filterMap.put(CodeBook.SELF_ORDER_BY, "assignTime desc");
        return userTaskDao.listObjectsByProperties(filterMap, offset, maxsize);
    }

    @Override
    public List<UserTask> listOptTask(String osId, String optId, String optMethod, String optTag) {
        Map<String, Object> filterMap = new HashMap<>();
        filterMap.put("osId", osId);
        filterMap.put("optId", optId);
        filterMap.put("optMethod", optMethod);
        filterMap.put("optTag", optTag);
        filterMap.put("taskState", UserTask.TASK_STATE_ALLOCATED);
        filterMap.put(CodeBook.SELF_ORDER_BY, "assignTime desc");
        return userTaskDao.listObjects(filterMap);
    }

    @Override
    public Long countUserTask(String userCode) {
        Map<String, Object> filterMap = new HashMap<>();
        filterMap.put("userCode", userCode);
        filterMap.put("taskState", UserTask.TASK_STATE_ALLOCATED);
        return userTaskDao.countUserTask(filterMap);
    }

    @Override
    public Long countUserTask(Map<String, Object> filterMap) {
        filterMap.put("taskState", UserTask.TASK_STATE_ALLOCATED);
        return userTaskDao.countUserTask(filterMap);
    }

    @Override
    public Long countUserCompleteTask(String userCode) {
        Map<String, Object> filterMap = new HashMap<>();
        filterMap.put("userCode", userCode);
        filterMap.put("taskState", UserTask.TASK_STATE_COMPLETED);
        return userTaskDao.countUserTask(filterMap);
    }

    @Override
    public Long countUserCompleteTask(Map<String, Object> filterMap) {
        filterMap.put("taskState", UserTask.TASK_STATE_COMPLETED);
        return userTaskDao.countUserTask(filterMap);
    }

    @Override
    public boolean checkTaskAuth(String taskId, String userCode) {
        UserTask task = userTaskDao.getObjectById(taskId);
        if (task == null) {
            throw new ObjectException("未获取到任务！");
        }
        Map<String, Object> filterMap = new HashMap<>();
        filterMap.put("taskId", taskId);
        filterMap.put("userCode", userCode);
        Long countUserTask = userTaskDao.countUserTask(filterMap);
        return countUserTask == 0 ? false : true;
    }

    @Override
    @Transactional
    public void completeTask(String userCode, String taskId) {
        UserTask userTask = userTaskDao.getObjectById(taskId);
        // 校验任务的操作人和状态
        checkUserTask(userCode, userTask);
        userTask.setTaskState(UserTask.TASK_STATE_COMPLETED);
        userTask.setUpdateTime(new Date());
        userTaskDao.updateObject(userTask);
    }

    /**
     * 校验任务的操作人和状态
     *
     * @param userCode
     * @param userTask 只要状态为A(办理中)的任务才能操作
     */
    private void checkUserTask(String userCode, UserTask userTask) {
        if (userTask == null) {
            throw new ObjectException("未获取到用户任务！");
        }
        if (userCode == null || !userCode.equals(userTask.getUserCode())) {
            throw new ObjectException("用户没有操作任务的权限！");
        }
        if (!UserTask.TASK_STATE_ALLOCATED.equals(userTask.getTaskState())) {
            throw new ObjectException("任务的状态为" + userTask.getTaskState());
        }
    }

    @Override
    @Transactional
    public void closeTask(String userCode, String taskId) {
        UserTask userTask = userTaskDao.getObjectById(taskId);
        // 校验任务的操作人和状态
        checkUserTask(userCode, userTask);
        userTask.setTaskState(UserTask.TASK_STATE_CLOSE);
        userTask.setUpdateTime(new Date());
        userTaskDao.updateObject(userTask);
    }

    @Override
    public void closeOptTask(String osId, String optId, String optMethod, String optTag) {
        boolean anyEmpty = StringUtils.isAnyEmpty(osId, optId, optMethod, optTag);
        if (anyEmpty) {
            throw new ObjectException("osId, optId, optMethod, optTag中 有参数为空");
        }
        List<UserTask> userTasks = this.listOptTask(osId, optId, optMethod, optTag);
        Date currentTime = new Date();
        userTasks.forEach(t -> {
            t.setUpdateTime(currentTime);
            t.setTaskState(UserTask.TASK_STATE_CLOSE);
            userTaskDao.updateObject(t);
        });
    }

    @Override
    @Transactional
    public void transferTask(String taskId, String userCode, String newOperator, String transDesc) {
        boolean anyEmpty = StringUtils.isAnyEmpty(taskId, userCode, newOperator);
        if (anyEmpty) {
            throw new ObjectException("taskId, userCode, newOperator中 有参数为空");
        }
        UserTask userTask = userTaskDao.getObjectById(taskId);
        Date currentTime = new Date();
        // 校验任务的操作人和状态
        checkUserTask(userCode, userTask);
        userTask.setUpdateTime(currentTime);
        userTask.setTaskState(UserTask.TASK_STATE_TRANSFER);
        // (1)更新用户任务的时间和状态
        userTaskDao.updateObject(userTask);

        // (2)新增任务
        userTask.setTaskId(null);
        userTask.setAssignTime(currentTime);
        userTask.setUpdateTime(null);
        userTask.setTaskState(UserTask.TASK_STATE_ALLOCATED);
        userTask.setUserCode(newOperator);
        userTask.setAuthDesc("转移用户（" + userCode + "）：" + transDesc);
        // todo 更新unitCode
//        CodeRepositoryUtil.getUserInfoByCode(userCode).getPrimaryUnit();
//        userTask.setUnitCode();
        userTaskDao.saveNewObject(userTask);

        // (3)记录日志
        TaskTransfer taskTransfer = new TaskTransfer(taskId, userCode, newOperator, transDesc, userCode, currentTime);
        transferDao.saveNewObject(taskTransfer);
    }

    @Override
    @Transactional
    public void userRoleDepute(RoleDepute depute) {

    }

    @Override
    @Transactional
    public void deleteUserTaskById(String taskId) {
        userTaskDao.deleteObjectById(taskId);
    }

    @Override
    @Transactional
    public void deleteUserDepute(String relegateId) {
        roleDeputeDao.deleteObjectById(relegateId);
    }

    @Override
    @Transactional
    public UserTask getUserTaskById(String taskId) {
        return userTaskDao.getObjectById(taskId);
    }
}
