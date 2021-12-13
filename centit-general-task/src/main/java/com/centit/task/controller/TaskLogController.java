package com.centit.task.controller;

import com.centit.framework.core.controller.BaseController;
import com.centit.framework.core.controller.WrapUpResponseBody;
import com.centit.framework.core.dao.PageQueryResult;
import com.centit.support.database.utils.PageDesc;
import com.centit.task.po.TaskLog;
import com.centit.task.service.TaskLogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
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
    public PageQueryResult<TaskLog> listAllTaskLog(PageDesc pageDesc, HttpServletRequest request) {
        Map<String, Object> filterMap = BaseController.collectRequestParameters(request);
        List<TaskLog> listObjects = taskLogService.listTaskLogs(filterMap, pageDesc);

        return PageQueryResult.createResultMapDict(listObjects, pageDesc);
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
        taskLogService.saveTaskLog(taskLog);
        return taskLog;
    }

    @ApiOperation(value = "删除任务日志", notes = "删除任务日志")
    @WrapUpResponseBody
    @RequestMapping(value = "/{logId}", method = RequestMethod.DELETE)
    public void deleteFlowRoleByCode(@PathVariable String logId) {
        taskLogService.deleteTaskLogByCode(logId);
    }
}
