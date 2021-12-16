package com.centit.task.dao;

import com.centit.framework.core.dao.CodeBook;
import com.centit.framework.jdbc.dao.BaseDaoImpl;
import com.centit.framework.jdbc.dao.DatabaseOptUtils;
import com.centit.task.po.TaskInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

/**
 * @author liu_cc
 * @create 2021-07-01 17:38
 */
@Repository
public class TaskInfoDao extends BaseDaoImpl<TaskInfo, String> {

    protected Logger logger = LoggerFactory.getLogger(TaskInfoDao.class);

    @Override
    public Map<String, String> getFilterField() {
        Map<String, String> filterField = new HashMap<>();
        filterField.put("taskId", CodeBook.EQUAL_HQL_ID);
        filterField.put("taskTitle", CodeBook.LIKE_HQL_ID);
        filterField.put("taskContent", CodeBook.LIKE_HQL_ID);
        filterField.put("taskOfficer", CodeBook.EQUAL_HQL_ID);
        filterField.put("taskReporter", CodeBook.EQUAL_HQL_ID);
        filterField.put("unitCode", CodeBook.EQUAL_HQL_ID);
        filterField.put("userCode", CodeBook.EQUAL_HQL_ID);
        filterField.put("osId", CodeBook.EQUAL_HQL_ID);
        filterField.put("optId", CodeBook.EQUAL_HQL_ID);
        filterField.put("optMethod", CodeBook.EQUAL_HQL_ID);
        filterField.put("(splitforin)taskState", CodeBook.IN_HQL_ID);
        filterField.put("(splitforin)taskClass", CodeBook.IN_HQL_ID);
        filterField.put("(splitforin)taskPriority", CodeBook.IN_HQL_ID);

        return filterField;
    }

    /**
     * WORKLOAD字段自增increment
     * @param increment 增加数量
     * @param taskId 任务id
     */
    @Transactional
    public void incrementWorkload(long increment,String taskId){
         DatabaseOptUtils.doExecuteSql(this, " UPDATE F_TASK_INFO SET WORKLOAD = WORKLOAD + ?  WHERE TASK_ID = ? ",
             new Object[]{increment, taskId});
        logger.info("incrementWorkload ：  UPDATE F_TASK_INFO SET WORKLOAD = WORKLOAD + ?  WHERE TASK_ID = ?  参数:{}, {} ",increment,taskId);
    }

    /**
     * WORKLOAD字段自减decrement
     * @param decrement 减少属性
     * @param taskId 任务id
     */
    @Transactional
    public void decrementWorkload(long decrement,String taskId){
        DatabaseOptUtils.doExecuteSql(this, " UPDATE F_TASK_INFO SET WORKLOAD =  WORKLOAD - ?    WHERE TASK_ID = ? ",
            new Object[]{decrement, taskId});
        logger.info("decrementWorkload ：   UPDATE F_TASK_INFO SET WORKLOAD =  WORKLOAD - ?    WHERE TASK_ID = ?   参数:{}, {} ",decrement,taskId);
    }
}
