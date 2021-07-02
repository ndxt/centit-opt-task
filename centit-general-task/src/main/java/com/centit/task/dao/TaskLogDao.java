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

        return filterField;
    }
}
