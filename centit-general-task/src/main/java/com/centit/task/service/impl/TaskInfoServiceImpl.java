package com.centit.task.service.impl;

import com.centit.support.database.utils.PageDesc;
import com.centit.task.dao.TaskInfoDao;
import com.centit.task.po.TaskInfo;
import com.centit.task.service.TaskInfoService;
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
public class TaskInfoServiceImpl implements TaskInfoService {

    @Autowired
    private TaskInfoDao taskInfoDao;

    @Override
    @Transactional
    public List<TaskInfo> listTaskInfos(Map<String, Object> filterMap, PageDesc pageDesc) {
        return taskInfoDao.listObjectsByProperties(filterMap, pageDesc);
    }

    @Override
    @Transactional
    public TaskInfo getTaskInfoByCode(String taskId) {
        return taskInfoDao.getObjectById(taskId);
    }

    @Override
    @Transactional
    public void saveTaskInfo(TaskInfo taskInfo) {
        taskInfoDao.mergeObject(taskInfo);
    }

    @Override
    @Transactional
    public void deleteTaskInfoByCode(String taskId) {
        taskInfoDao.deleteObjectById(taskId);
    }

}
