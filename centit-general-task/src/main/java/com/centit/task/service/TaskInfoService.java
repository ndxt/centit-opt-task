package com.centit.task.service;

import com.centit.support.database.utils.PageDesc;
import com.centit.task.po.TaskInfo;

import java.util.List;
import java.util.Map;

/**
 * @author liu_cc
 * @create 2021-07-01 17:36
 */
public interface TaskInfoService {
    List<TaskInfo> listTaskInfos(Map<String, Object> filterMap, PageDesc pageDesc);

    TaskInfo getTaskInfoByCode(String taskId);

    void saveTaskInfo(TaskInfo taskInfo);

    void deleteTaskInfoByCode(String taskId);

    void updateTaskInfo(TaskInfo taskInfo);

    void incrementWorkload(long increament,String taskId);
}
