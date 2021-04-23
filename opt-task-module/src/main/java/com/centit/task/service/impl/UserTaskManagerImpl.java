package com.centit.task.service.impl;

import com.centit.framework.core.dao.CodeBook;
import com.centit.support.database.utils.PageDesc;
import com.centit.task.dao.UserTaskDao;
import com.centit.task.po.RoleDepute;
import com.centit.task.po.UserTask;
import com.centit.task.service.UserTaskManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author liu_cc
 * @create 2021-04-23 14:35
 */
@Service
@Slf4j
public class UserTaskManagerImpl implements UserTaskManager {

    @Autowired
    private UserTaskDao userTaskDao;

    @Override
    public String saveUserTask(UserTask userTask) {
        userTask.setAssignTime(new Date());
        userTaskDao.mergeObject(userTask);
        return userTask.getTaskId();
    }

    @Override
    public List<UserTask> listUserTask(String userCode, int offset, int maxsize) {
        return userTaskDao.listUserTask(userCode, offset, maxsize);
    }

    @Override
    public List<UserTask> listUserTask(Map<String, Object> filterMap, PageDesc pageDesc) {
        filterMap.put(CodeBook.SELF_ORDER_BY, "assignTime desc");
        return userTaskDao.listObjects(filterMap, pageDesc);
    }

    @Override
    public List<UserTask> listUserTask(Map<String, Object> filterMap, int offset, int maxsize) {
        return null;
    }

    @Override
    public List<UserTask> listUserCompleteTask(String userCode, int offset, int maxsize) {
        return null;
    }

    @Override
    public List<UserTask> listUserCompleteTask(Map<String, Object> filterMap, int offset, int maxsize) {
        return null;
    }

    @Override
    public List<UserTask> listOptTask(String osId, String optId, String optMethod, String optTag) {
        return null;
    }

    @Override
    public Long countUserTask(String userCode) {
        return null;
    }

    @Override
    public Long countUserTask(Map<String, Object> filterMap) {
        return null;
    }

    @Override
    public Long countUserCompleteTask(String userCode) {
        return null;
    }

    @Override
    public Long countUserCompleteTask(Map<String, Object> filterMap) {
        return null;
    }

    @Override
    public boolean checkTaskAuth(String taskId, String userCode) {
        return false;
    }

    @Override
    public void completeTask(String userCode, String taskId) {

    }

    @Override
    public void closeTask(String userCode, String taskId) {

    }

    @Override
    public void closeOptTask(String osId, String optId, String optMethod, String optTag) {

    }

    @Override
    public void transferTask(String taskId, String userCode, String newOperator, String transDesc) {

    }

    @Override
    public void userRoleDepute(RoleDepute depute) {

    }

    @Override
    public void deleteUserDepute(RoleDepute depute) {

    }
}
