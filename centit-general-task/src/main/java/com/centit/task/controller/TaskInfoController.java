package com.centit.task.controller;

import com.centit.framework.core.controller.BaseController;
import com.centit.framework.core.controller.WrapUpResponseBody;
import com.centit.framework.core.dao.PageQueryResult;
import com.centit.support.database.utils.PageDesc;
import com.centit.task.po.TaskInfo;
import com.centit.task.service.TaskInfoService;
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
@Api(value = "任务信息", tags = "任务信息接口类")
@RequestMapping("/opt/task")
@Slf4j
public class TaskInfoController extends BaseController {

    @Autowired
    private TaskInfoService taskInfoService;

    @ApiOperation(value = "任务信息列表", notes = "任务信息列表")
    @WrapUpResponseBody
    @RequestMapping(method = RequestMethod.GET)
    public PageQueryResult<TaskInfo> listAllTaskInfo(PageDesc pageDesc, HttpServletRequest request) {
        Map<String, Object> filterMap = BaseController.collectRequestParameters(request);
        List<TaskInfo> listObjects = taskInfoService.listTaskInfos(filterMap, pageDesc);

        return PageQueryResult.createResult(listObjects, pageDesc);
    }

    @ApiOperation(value = "查询单个任务信息", notes = "查询单个任务信息")
    @WrapUpResponseBody
    @RequestMapping(value = "/{taskId}", method = RequestMethod.GET)
    public TaskInfo getTaskInfoByCode(@PathVariable String taskId) {
        return taskInfoService.getTaskInfoByCode(taskId);
    }

    @ApiOperation(value = "保存任务信息", notes = "保存任务信息")
    @WrapUpResponseBody
    @RequestMapping(method = RequestMethod.POST)
    public TaskInfo saveFlowRole(@RequestBody TaskInfo taskInfo) {
        taskInfoService.saveTaskInfo(taskInfo);
        return taskInfo;
    }

    @ApiOperation(value = "删除任务信息", notes = "删除任务信息")
    @WrapUpResponseBody
    @RequestMapping(value = "/{taskId}", method = RequestMethod.DELETE)
    public void deleteFlowRoleByCode(@PathVariable String taskId) {
        taskInfoService.deleteTaskInfoByCode(taskId);
    }
}
