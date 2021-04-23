package com.centit.task.controller;

import com.centit.framework.core.controller.BaseController;
import com.centit.framework.core.controller.WrapUpResponseBody;
import com.centit.framework.core.dao.PageQueryResult;
import com.centit.support.database.utils.PageDesc;
import com.centit.task.po.UserTask;
import com.centit.task.service.UserTaskManager;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * @author liu_cc
 * @create 2021-04-22 17:24
 */
@Controller
@Api(value = "用户任务", tags = "用户任务接口类")
@RequestMapping("/opt/task")
@Slf4j
public class UserTaskController {

    @Autowired
    private UserTaskManager userTaskManager;

    @ApiOperation(value = "保存或修改用户任务", notes = "保存或修改用户任务")
    @WrapUpResponseBody
    @PostMapping
    public String saveUserTask(@RequestBody UserTask userTask) {
        String taskId = userTaskManager.saveUserTask(userTask);
        return taskId;
    }

    /*@ApiOperation(value = "删除用户任务", notes = "删除用户任务")
    @WrapUpResponseBody
    @RequestMapping(value = "/{taskId}", method = RequestMethod.DELETE)
    public void deleteUserTaskById(@PathVariable String taskId) {
        userTaskManager.deleteUserTaskById(taskId);
    }


    @ApiOperation(value = "获取用户任务", notes = "获取用户任务")
    @WrapUpResponseBody
    @RequestMapping(value = "/{taskId}", method = RequestMethod.GET)
    public UserTask getUserTaskById(@PathVariable String taskId) {
        return userTaskManager.getUserTaskById(taskId);
    }*/

    @ApiOperation(value = "获取所有用户任务", notes = "获取所有用户任务")
    @WrapUpResponseBody
    @RequestMapping(value = "/allUserTasks", method = RequestMethod.GET)
    public List<UserTask> listUserTask(HttpServletRequest request) {
        Map<String, Object> filterMap = BaseController.collectRequestParameters(request);
        return userTaskManager.listUserTask(filterMap, null);
    }

    @ApiOperation(value = "分页获取用户任务", notes = "分页获取用户任务")
    @WrapUpResponseBody
    @RequestMapping(method = RequestMethod.GET)
    public PageQueryResult listUserTask(PageDesc pageDesc, HttpServletRequest request) {
        Map<String, Object> filterMap = BaseController.collectRequestParameters(request);
        List<UserTask> userTasks = userTaskManager.listUserTask(filterMap, pageDesc);
        return PageQueryResult.createResult(userTasks, pageDesc);
    }


    @ApiOperation(value = "获取用户任务", notes = "任务按时间倒序排列")
    @WrapUpResponseBody
    @RequestMapping(value = "/listUserTask/{userCode}/{offset}/{maxsize}", method = RequestMethod.GET)
    public List<UserTask> listUserTask(@PathVariable String userCode, @PathVariable int offset, @PathVariable int maxsize) {
        return userTaskManager.listUserTask(userCode, offset, maxsize);
    }
}
