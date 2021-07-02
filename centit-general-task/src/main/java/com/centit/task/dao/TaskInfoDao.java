package com.centit.task.dao;

import com.centit.framework.core.dao.CodeBook;
import com.centit.framework.jdbc.dao.BaseDaoImpl;
import com.centit.task.po.TaskInfo;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

/**
 * @author liu_cc
 * @create 2021-07-01 17:38
 */
@Repository
public class TaskInfoDao extends BaseDaoImpl<TaskInfo, String> {
    @Override
    public Map<String, String> getFilterField() {
        Map<String, String> filterField = new HashMap<>();
        filterField.put("taskId", CodeBook.EQUAL_HQL_ID);

        return filterField;
    }
}
