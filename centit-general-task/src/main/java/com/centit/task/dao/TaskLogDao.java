package com.centit.task.dao;

import com.centit.framework.core.dao.CodeBook;
import com.centit.framework.jdbc.dao.BaseDaoImpl;
import com.centit.task.po.TaskLog;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

/**
 * @author liu_cc
 * @create 2021-07-01 17:38
 */
@Repository
public class TaskLogDao extends BaseDaoImpl<TaskLog, String> {
    @Override
    public Map<String, String> getFilterField() {
        Map<String, String> filterField = new HashMap<>();
        filterField.put("logId", CodeBook.EQUAL_HQL_ID);
        filterField.put("taskId",CodeBook.EQUAL_HQL_ID);
        filterField.put("logType",CodeBook.EQUAL_HQL_ID);
        filterField.put("userCode",CodeBook.EQUAL_HQL_ID);
        filterField.put("logContent",CodeBook.LIKE_HQL_ID);
        //工作时间大于0
        filterField.put("validWorkLoad"," WORKLOAD > 0  ");
        //工作时间等于0
        filterField.put("invalidWorkLoad"," WORKLOAD = 0 ");
        return filterField;
    }
}
