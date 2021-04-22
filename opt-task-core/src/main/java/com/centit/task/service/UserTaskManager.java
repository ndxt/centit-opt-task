package com.centit.task.service;

import com.centit.task.po.RoleDepute;
import com.centit.task.po.UserTask;

import java.util.List;
import java.util.Map;

/**
 * * 新建任务
 * * 任务查询
 * * 检测用户任务权限
 * * 完成任务
 * * 关闭任务
 * * 任务委托
 * * 任务转移
 */
public interface UserTaskManager {
    /**
     * 新建任务
     * @param userTask 任务信息
     * @return 任务id
     */
    String createTask(UserTask userTask);

    /**
     *
     * @param userCode 用户
     * @param offset 起始条目
     * @param maxsize 最到返回条目
     * @return 用户任务列表
     */
    List<UserTask> listUserTask(String userCode, int offset, int maxsize);

    /**
     *
     * @param filterMap 查询过滤条件
     * @param offset 起始条目
     * @param maxsize 最到返回条目
     * @return 用户任务列表
     */
    List<UserTask> listUserTask(Map<String, Object> filterMap, int offset, int maxsize);

    /**
     * 用户任务条目计数
     * @param userCode 用户代码
     * @return 用户任务列表
     */
    Long countUserTask(String userCode);

    /**
     * 用户任务条目计数
     * @param filterMap 查询过滤条件
     * @return 用户任务列表
     */
    Long countUserTask(Map<String, Object> filterMap);

    /**
     * 鉴权
     * @param taskId 任务
     * @param userCode 用户代码
     * @return 是否有权限操作任务
     */
    boolean checkTaskAuth(String taskId, String userCode);

    /**
     * 完成任务
     * @param userCode 用户代码
     * @param taskId 任务
     */
    void completeTask(String userCode, String taskId);

    /**
     * 关闭任务
     * @param userCode 用户代码
     * @param taskId 任务
     */
    void closeTask(String userCode, String taskId);

    /**
     * 转移任务
     * @param userCode 用户代码
     * @param taskId 任务
     * @param newOperator 新的操作人员
     * @param transDesc 转移说明
     */
    void transferTask(String taskId, String userCode,
                      String newOperator, String transDesc);

    /**
     * 任务委托
     * @param depute 委托明细
     */
    void userRoleDepute(RoleDepute depute);
}
