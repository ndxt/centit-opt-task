package com.centit.task.dao;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.centit.framework.components.CodeRepositoryUtil;
import com.centit.framework.core.dao.CodeBook;
import com.centit.framework.jdbc.dao.BaseDaoImpl;
import com.centit.framework.jdbc.dao.DatabaseOptUtils;
import com.centit.support.common.WorkTimeSpan;
import com.centit.support.database.utils.QueryAndNamedParams;
import com.centit.support.database.utils.QueryUtils;
import com.centit.task.po.TaskInfo;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

import static com.sun.tools.classfile.Attribute.Code;

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
     *
     * @param increment 增加数量
     * @param taskId    任务id
     */
    @Transactional
    public void incrementWorkload(long increment, String taskId) {
        DatabaseOptUtils.doExecuteSql(this, " UPDATE F_TASK_INFO SET WORKLOAD = WORKLOAD + ?  WHERE TASK_ID = ? ",
            new Object[]{increment, taskId});
        logger.info("incrementWorkload ：  UPDATE F_TASK_INFO SET WORKLOAD = WORKLOAD + ?  WHERE TASK_ID = ?  参数:{}, {} ", increment, taskId);
    }

    /**
     * WORKLOAD字段自减decrement
     *
     * @param decrement 减少属性
     * @param taskId    任务id
     */
    @Transactional
    public void decrementWorkload(long decrement, String taskId) {
        DatabaseOptUtils.doExecuteSql(this, " UPDATE F_TASK_INFO SET WORKLOAD =  WORKLOAD - ?    WHERE TASK_ID = ? ",
            new Object[]{decrement, taskId});
        logger.info("decrementWorkload ：   UPDATE F_TASK_INFO SET WORKLOAD =  WORKLOAD - ?    WHERE TASK_ID = ?   参数:{}, {} ", decrement, taskId);
    }

    public JSONArray statTaskInfo(Map<String, Object> filterMap) {
        String sql = "SELECT task_state,count(0) task_count,sum(WORKLOAD) do_work,sum(ESTIMATE_WORKLOAD) sum_work FROM f_task_info where task_state in ('A','B','C')" +
            "[:unitCode | and unit_code=:unitCode] [:taskOfficer | and task_officer=:taskOfficer]" +
            "[:taskReporter | and task_reporter=:taskReporter] [:osId | and os_id=:osId] " +
            "group by TASK_STATE";
        QueryAndNamedParams qap = QueryUtils.translateQuery(sql, filterMap);
        WorkTimeSpan workTimeSpan = new WorkTimeSpan();
        JSONArray jsonArray = DatabaseOptUtils.listObjectsByNamedSqlAsJson(this, qap.getQuery(), qap.getParams());
        for (Object object : jsonArray) {
            JSONObject jsonObject = (JSONObject) object;
            String taskState = jsonObject.getString("taskState");
            int doWork = jsonObject.getIntValue("doWork");
            int sumWork = jsonObject.getIntValue("sumWork");
            switch (taskState) {
                case "A":
                    workTimeSpan.fromNumberAsMinute(sumWork);
                    break;
                case "B":
                    workTimeSpan.fromNumberAsMinute(sumWork-doWork);
                    break;
                case "C":
                    workTimeSpan.fromNumberAsMinute(doWork);
                    break;
                default:
            }
            jsonObject.put("workload", workTimeSpan.toStringAsMinute().toLowerCase());
        }
        return jsonArray;
    }
    public JSONArray statPersonalTask(Map<String, Object> filterMap) {
        String sql = "SELECT os_id,task_state,count(0) task_count,sum(WORKLOAD) do_work,sum(ESTIMATE_WORKLOAD) sum_work FROM f_task_info where task_state in ('A','B','C')" +
            "[:unitCode | and unit_code=:unitCode] " +
            "[:taskOfficer | and task_officer=:taskOfficer] " +
            "group by TASK_STATE,os_id";
        QueryAndNamedParams qap = QueryUtils.translateQuery(sql, filterMap);
        WorkTimeSpan workTimeSpan = new WorkTimeSpan();
        JSONArray jsonArray = DatabaseOptUtils.listObjectsByNamedSqlAsJson(this, qap.getQuery(), qap.getParams());
        for (Object object : jsonArray) {
            JSONObject jsonObject = (JSONObject) object;
            String osId = jsonObject.getString("osId");
            String osName = CodeRepositoryUtil.getValue("osId",osId);
            jsonObject.put("osName",osName);
            String taskState = jsonObject.getString("taskState");
            int doWork = jsonObject.getIntValue("doWork");
            int sumWork = jsonObject.getIntValue("sumWork");
            switch (taskState) {
                case "A":
                    workTimeSpan.fromNumberAsMinute(sumWork);
                    break;
                case "B":
                    workTimeSpan.fromNumberAsMinute(sumWork-doWork);
                    break;
                case "C":
                    workTimeSpan.fromNumberAsMinute(doWork);
                    break;
                default:
            }
            jsonObject.put("workload", workTimeSpan.toStringAsMinute().toLowerCase());
        }
        return jsonArray;
    }
    public JSONArray statMember(Map<String, Object> filterMap) {
        String sql = "SELECT task_Officer,task_state,count(0) task_count,sum(WORKLOAD) do_work,sum(ESTIMATE_WORKLOAD) sum_work FROM f_task_info where task_state in ('A','B','C')" +
            "[:unitCode | and unit_code=:unitCode] " +
            "[:osId | and os_id=:osId] " +
            "group by TASK_STATE,task_Officer";
        QueryAndNamedParams qap = QueryUtils.translateQuery(sql, filterMap);
        WorkTimeSpan workTimeSpan = new WorkTimeSpan();
        JSONArray jsonArray = DatabaseOptUtils.listObjectsByNamedSqlAsJson(this, qap.getQuery(), qap.getParams());
        for (Object object : jsonArray) {
            JSONObject jsonObject = (JSONObject) object;
            String userCode = jsonObject.getString("taskOfficer");
            String userName = CodeRepositoryUtil.getValue("userCode",userCode);
            jsonObject.put("userName",userName);
            String taskState = jsonObject.getString("taskState");
            int doWork = jsonObject.getIntValue("doWork");
            int sumWork = jsonObject.getIntValue("sumWork");
            switch (taskState) {
                case "A":
                    workTimeSpan.fromNumberAsMinute(sumWork);
                    break;
                case "B":
                    workTimeSpan.fromNumberAsMinute(sumWork-doWork);
                    break;
                case "C":
                    workTimeSpan.fromNumberAsMinute(doWork);
                    break;
                default:
            }
            jsonObject.put("workload", workTimeSpan.toStringAsMinute().toLowerCase());
        }
        return jsonArray;
    }
}
