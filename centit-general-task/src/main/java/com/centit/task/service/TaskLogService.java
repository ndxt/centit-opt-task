package com.centit.task.service;

import com.centit.support.database.utils.PageDesc;
import com.centit.task.po.TaskLog;

import java.util.List;
import java.util.Map;

/**
 * @author liu_cc
 * @create 2021-07-01 17:36
 */
public interface TaskLogService {
    List<TaskLog> listTaskLogs(Map<String, Object> filterMap, PageDesc pageDesc);

    TaskLog getTaskLogByCode(String logId);

    void saveTaskLog(TaskLog taskLog);

    void deleteTaskLogByCode(String logId,String userCode);
}
