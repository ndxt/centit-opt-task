package com.centit.task.service.impl;

import com.centit.support.database.utils.PageDesc;
import com.centit.task.dao.TaskLogDao;
import com.centit.task.po.TaskLog;
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
    private TaskLogDao TaskLogDao;

    @Override
    @Transactional
    public List<TaskLog> listTaskLogs(Map<String, Object> filterMap, PageDesc pageDesc) {
        return TaskLogDao.listObjectsByProperties(filterMap, pageDesc);
    }

    @Override
    @Transactional
    public TaskLog getTaskLogByCode(String logId) {
        return TaskLogDao.getObjectById(logId);
    }

    @Override
    @Transactional
    public void saveTaskLog(TaskLog TaskLog) {
        TaskLogDao.mergeObject(TaskLog);
    }

    @Override
    @Transactional
    public void deleteTaskLogByCode(String logId) {
        TaskLogDao.deleteObjectById(logId);
    }

}
