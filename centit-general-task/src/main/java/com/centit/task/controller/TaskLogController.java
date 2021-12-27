package com.centit.task.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.centit.framework.common.WebOptUtils;
import com.centit.framework.core.controller.BaseController;
import com.centit.framework.core.controller.WrapUpResponseBody;
import com.centit.framework.core.dao.DictionaryMapUtils;
import com.centit.framework.core.dao.PageQueryResult;
import com.centit.support.common.ObjectException;
import com.centit.support.common.WorkTimeSpan;
import com.centit.support.database.utils.PageDesc;
import com.centit.task.po.TaskLog;
import com.centit.task.service.TaskLogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * @author liu_cc
 * @create 2021-07-01 17:31
 */
@Controller
@Api(value = "任务日志", tags = "任务日志接口类")
@RequestMapping("/general/log")
@Slf4j
public class TaskLogController extends BaseController {

    @Autowired
    private TaskLogService taskLogService;

    @ApiOperation(value = "任务日志列表", notes = "任务日志列表")
    @WrapUpResponseBody
    @RequestMapping(method = RequestMethod.GET)
    public PageQueryResult listAllTaskLog(PageDesc pageDesc, HttpServletRequest request) {
        Map<String, Object> filterMap = BaseController.collectRequestParameters(request);
        List<TaskLog> listObjects = taskLogService.listTaskLogs(filterMap, pageDesc);

        JSONArray jsonArray = DictionaryMapUtils.objectsToJSONArray(listObjects);
        for (Object object : jsonArray) {
            JSONObject jsonObject = (JSONObject) object;
            if ("system".equals(jsonObject.getString("userCode"))){
                jsonObject.put("userName","系统日志");
            }
            WorkTimeSpan workTimeSpan = new WorkTimeSpan();
            workTimeSpan.fromNumberAsMinute(jsonObject.getLongValue("workload"));
            jsonObject.put("workloadMinute",workTimeSpan.toStringAsMinute().toLowerCase());
        }
        return PageQueryResult.createResult(jsonArray, pageDesc);
    }

    @ApiOperation(value = "查询单个任务日志", notes = "查询单个任务日志")
    @WrapUpResponseBody
    @RequestMapping(value = "/{logId}", method = RequestMethod.GET)
    public TaskLog getTaskLogByCode(@PathVariable String logId) {
        return taskLogService.getTaskLogByCode(logId);
    }

    @ApiOperation(value = "保存任务日志", notes = "保存任务日志")
    @WrapUpResponseBody
    @RequestMapping(method = RequestMethod.POST)
    public TaskLog saveFlowRole(@RequestBody TaskLog taskLog) {
        if (null == taskLog.getWorkload()){
            taskLog.setWorkload(0L);
        }
        taskLogService.saveTaskLog(taskLog);
        return taskLog;
    }

    @ApiOperation(value = "删除任务日志", notes = "删除任务日志")
    @WrapUpResponseBody
    @RequestMapping(value = "/{logId}", method = RequestMethod.DELETE)
    public void deleteFlowRoleByCode(@PathVariable String logId,HttpServletRequest request) {
        String userCode = WebOptUtils.getCurrentUserCode(request);
        if (StringUtils.isBlank(userCode)){
            throw new ObjectException("您还未登录!");
        }
        taskLogService.deleteTaskLogByCode(logId,userCode);
    }
}
