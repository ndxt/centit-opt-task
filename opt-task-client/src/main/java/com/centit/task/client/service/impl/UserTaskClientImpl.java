package com.centit.task.client.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.centit.framework.appclient.HttpReceiveJSON;
import com.centit.framework.appclient.RestfulHttpRequest;
import com.centit.support.database.utils.PageDesc;
import com.centit.support.network.UrlOptUtils;
import com.centit.task.po.RoleDepute;
import com.centit.task.po.UserTask;
import com.centit.task.service.UserTaskManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author liu_cc
 * @create 2021-04-25 16:24
 */
@Service
public class UserTaskClientImpl implements UserTaskManager {

    public UserTaskClientImpl() {
    }

    private UserTaskAppSession appSession;

    @Autowired
    public void setAppSession(UserTaskAppSession appSession) {
        this.appSession = appSession;
    }

    @Override
    public String saveUserTask(UserTask userTask) {
        String returnJson = RestfulHttpRequest.jsonPost(appSession,
            "/opt/task", userTask);
        HttpReceiveJSON receiveJSON = HttpReceiveJSON.valueOfJson(returnJson);
        return receiveJSON.getDataAsString();
    }

    @Override
    public List<String> saveUserTaskList(List<UserTask> userTaskList) {
        String returnJson = RestfulHttpRequest.jsonPost(appSession,
            "/opt/task/saveUserTaskList", userTaskList);
        HttpReceiveJSON receiveJSON = HttpReceiveJSON.valueOfJson(returnJson);
        return receiveJSON.getDataAsArray("objList", String.class);
    }

    @Override
    public List<UserTask> listUserTask(String userCode, int offset, int maxsize) {
        return null;
    }

    /**
     * 任务按时间倒序排列
     *
     * @param filterMap 条件
     * @param pageDesc  分页
     * @return 用户任务列表
     */
    @Override
    public List<UserTask> listUserTask(Map<String, Object> filterMap, PageDesc pageDesc) {
        HttpReceiveJSON receiveJSON = RestfulHttpRequest.getResponseData(appSession,
            UrlOptUtils.appendParamsToUrl(
                UrlOptUtils.appendParamsToUrl("/opt/task/listUserTask",
                    filterMap), (JSONObject) JSON.toJSON(pageDesc)));
        RestfulHttpRequest.checkHttpReceiveJSON(receiveJSON);
        pageDesc.copy(receiveJSON.getDataAsObject("pageDesc", PageDesc.class));
        return receiveJSON.getDataAsArray("objList", UserTask.class);
    }

    @Override
    public List<UserTask> listUserTask(Map<String, Object> filterMap, int offset, int maxsize) {
        HttpReceiveJSON receiveJSON = RestfulHttpRequest.getResponseData(appSession,
            UrlOptUtils.appendParamsToUrl(
                "/opt/task/listUserTask/" + offset + "/" + maxsize, filterMap));
        return receiveJSON.getDataAsArray("objList", UserTask.class);
    }

    @Override
    public List<UserTask> listUserCompleteTask(String userCode, int offset, int maxsize) {
        return null;
    }

    @Override
    public List<UserTask> listUserCompleteTask(Map<String, Object> filterMap, PageDesc pageDesc) {
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
    public void deleteUserTaskById(String taskId) {

    }

    @Override
    public void deleteUserDepute(String relegateId) {

    }

    @Override
    public UserTask getUserTaskById(String taskId) {
        HttpReceiveJSON receiveJSON = RestfulHttpRequest.getResponseData(appSession,
            "/opt/task/" + taskId);
        return receiveJSON.getDataAsObject(UserTask.class);
    }
}
