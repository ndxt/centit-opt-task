package com.centit.task.dao;

import com.centit.framework.core.dao.CodeBook;
import com.centit.framework.jdbc.dao.BaseDaoImpl;
import com.centit.task.po.UserTask;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author liu_cc
 * @create 2021-04-23 14:35
 */
@Repository
public class UserTaskDao extends BaseDaoImpl<UserTask, String> {

    @Override
    public Map<String, String> getFilterField() {
        Map<String, String> filterField = new HashMap<>();
        filterField.put("osId", CodeBook.EQUAL_HQL_ID);
        filterField.put("optId", CodeBook.EQUAL_HQL_ID);
        filterField.put("optName", CodeBook.LIKE_HQL_ID);
        filterField.put("optMethod", CodeBook.EQUAL_HQL_ID);
        filterField.put("optTag", CodeBook.EQUAL_HQL_ID);
        filterField.put("userCode", CodeBook.EQUAL_HQL_ID);
        filterField.put("unitCode", CodeBook.EQUAL_HQL_ID);
        filterField.put("roleType", CodeBook.EQUAL_HQL_ID);
        filterField.put("roleCode", CodeBook.EQUAL_HQL_ID);
        filterField.put("taskState", CodeBook.EQUAL_HQL_ID);
        filterField.put(CodeBook.SELF_ORDER_BY, " assignTime desc ");
        filterField.put(CodeBook.ORDER_BY_HQL_ID, " assignTime desc ");
        return filterField;
    }

    /**
     * 任务按时间倒序排列
     *
     * @param userCode 用户
     * @param offset   起始条目
     * @param maxsize  最大返回条目
     * @return 用户任务列表
     */
    public List<UserTask> listUserTask(String userCode, int offset, int maxsize) {
        return this.listObjectsByFilter(" where TASK_STATE = 'A' USER_CODE = ? ORDER BY ASSIGN_TIME DESC limit ?,? ",
            new Object[]{userCode, offset, maxsize});
    }

    /**
     * 任务按时间倒序排列
     *
     * @param userCode 用户
     * @param offset   起始条目
     * @param maxsize  最大返回条目
     * @return 用户已完成任务列表
     */
    public List<UserTask> listUserCompleteTask(String userCode, int offset, int maxsize) {
        return this.listObjectsByFilter(" where TASK_STATE = 'C' USER_CODE = ? ORDER BY ASSIGN_TIME DESC limit ?,? ",
            new Object[]{userCode, offset, maxsize});
    }

    public Long countUserTask(Map<String, Object> filterMap) {
        return Long.valueOf(this.countObject(filterMap));
    }
}
