package com.centit.task.controller;

import com.centit.framework.core.controller.BaseController;
import com.centit.framework.core.controller.WrapUpResponseBody;
import com.centit.framework.core.dao.PageQueryResult;
import com.centit.support.database.utils.PageDesc;
import com.centit.task.po.UserTask;
import com.centit.task.service.UserTaskManager;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
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
public class UserTaskController extends BaseController {

    @Autowired
    private UserTaskManager userTaskManager;

    @ApiOperation(value = "保存或修改用户任务", notes = "保存或修改用户任务")
    @WrapUpResponseBody
    @PostMapping
    public String saveUserTask(@RequestBody UserTask userTask) {
        String taskId = userTaskManager.saveUserTask(userTask);
        return taskId;
    }

    @ApiOperation(value = "批量保存用户任务", notes = "批量保存用户任务")
    @WrapUpResponseBody
    @PostMapping(value = "/saveUserTaskList")
    public List<String> saveUserTaskList(@RequestBody List<UserTask> userTaskList) {
        return userTaskManager.saveUserTaskList(userTaskList);
    }


    @ApiOperation(value = "获取用户任务", notes = "获取用户任务")
    @WrapUpResponseBody
    @RequestMapping(value = "/{taskId}", method = RequestMethod.GET)
    public UserTask getUserTaskById(@PathVariable String taskId) {
        return userTaskManager.getUserTaskById(taskId);
    }

    @ApiOperation(value = "获取所有用户任务", notes = "任务按时间倒序排列")
    @WrapUpResponseBody
    @RequestMapping(value = "/allUserTasks", method = RequestMethod.GET)
    public List<UserTask> listUserTask(HttpServletRequest request) {
        Map<String, Object> filterMap = BaseController.collectRequestParameters(request);
        return userTaskManager.listUserTask(filterMap, null);
    }

    @ApiOperation(value = "分页获取用户任务", notes = "任务按时间倒序排列")
    @WrapUpResponseBody
    @GetMapping(value = "/listUserTask")
    public PageQueryResult listUserTask(PageDesc pageDesc, HttpServletRequest request) {
        Map<String, Object> filterMap = BaseController.collectRequestParameters(request);
        List<UserTask> userTasks = userTaskManager.listUserTask(filterMap, pageDesc);
        return PageQueryResult.createResult(userTasks, pageDesc);
    }


    @ApiOperation(value = "根据userCode获取用户任务", notes = "任务按时间倒序排列")
    @WrapUpResponseBody
    @RequestMapping(value = "/listUserTask/{userCode}/{offset}/{maxsize}", method = RequestMethod.GET)
    public List<UserTask> listUserTask(@PathVariable String userCode, @PathVariable int offset, @PathVariable int maxsize) {
        return userTaskManager.listUserTask(userCode, offset, maxsize);
    }

    @ApiOperation(value = "用户任务查询", notes = "任务按时间倒序排列")
    @ApiImplicitParams({@ApiImplicitParam(
        name = "offset", value = "记录行的偏移量",
        required = true, paramType = "path", dataType = "int"
    ), @ApiImplicitParam(
        name = "maxsize", value = "记录行的最大数目",
        required = true, paramType = "path", dataType = "int"
    ), @ApiImplicitParam(name = "userCode", value = "操作用户"),
        @ApiImplicitParam(name = "osId", value = "业务系统id:等同于APPLICATION_ID,对应应用系统，比如工作流引擎workflow、考勤系统id"),
        @ApiImplicitParam(name = "optId", value = "业务模块:对应工作流中的流程代码，考勤系统中的功能")
    })
    @WrapUpResponseBody
    @RequestMapping(value = "/listUserTask/{offset}/{maxsize}", method = RequestMethod.GET)
    public List<UserTask> listUserTask(@PathVariable int offset, @PathVariable int maxsize, HttpServletRequest request) {
        Map<String, Object> filterMap = BaseController.collectRequestParameters(request);
        return userTaskManager.listUserTask(filterMap, offset, maxsize);
    }

    @ApiOperation(value = "分页获取用户已完成任务", notes = "任务按时间倒序排列")
    @WrapUpResponseBody
    @GetMapping(value = "/listUserCompleteTask")
    public PageQueryResult listUserCompleteTask(PageDesc pageDesc, HttpServletRequest request) {
        Map<String, Object> filterMap = BaseController.collectRequestParameters(request);
        List<UserTask> userTasks = userTaskManager.listUserCompleteTask(filterMap, pageDesc);
        return PageQueryResult.createResult(userTasks, pageDesc);
    }

    @ApiOperation(value = "根据userCode获取用户已完成任务列表", notes = "任务按时间倒序排列")
    @WrapUpResponseBody
    @RequestMapping(value = "/listUserCompleteTask/{userCode}/{offset}/{maxsize}", method = RequestMethod.GET)
    public List<UserTask> listUserCompleteTask(@PathVariable String userCode, @PathVariable int offset, @PathVariable int maxsize) {
        return userTaskManager.listUserCompleteTask(userCode, offset, maxsize);
    }

    @ApiOperation(value = "用户已完成任务查询", notes = "任务按时间倒序排列")
    @ApiImplicitParams({@ApiImplicitParam(
        name = "offset", value = "记录行的偏移量",
        required = true, paramType = "path", dataType = "int"
    ), @ApiImplicitParam(
        name = "maxsize", value = "记录行的最大数目",
        required = true, paramType = "path", dataType = "int"
    ), @ApiImplicitParam(name = "userCode", value = "操作用户"),
        @ApiImplicitParam(name = "osId", value = "业务系统id:等同于APPLICATION_ID,对应应用系统，比如工作流引擎workflow、考勤系统id"),
        @ApiImplicitParam(name = "optId", value = "业务模块:对应工作流中的流程代码，考勤系统中的功能")
    })
    @WrapUpResponseBody
    @RequestMapping(value = "/listUserCompleteTask/{offset}/{maxsize}", method = RequestMethod.GET)
    public List<UserTask> listUserCompleteTask(@PathVariable int offset, @PathVariable int maxsize, HttpServletRequest request) {
        Map<String, Object> filterMap = BaseController.collectRequestParameters(request);
        return userTaskManager.listUserCompleteTask(filterMap, offset, maxsize);
    }


    @ApiOperation(value = "业务任务列表", notes = "任务按时间倒序排列")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "osId", value = "应用代码"),
        @ApiImplicitParam(name = "optId", value = "业务代码"),
        @ApiImplicitParam(name = "optId", value = "业务方法、节点"),
        @ApiImplicitParam(name = "optId", value = "业务主键")
    })
    @WrapUpResponseBody
    @RequestMapping(value = "/listOptTask", method = RequestMethod.GET)
    public List<UserTask> listOptTask(String osId, String optId, String optMethod, String optTag, HttpServletRequest request) {
        return userTaskManager.listOptTask(osId, optId, optMethod, optTag);
    }

    @ApiOperation(value = "根据userCode获取用户任务条目计数", notes = "根据userCode获取用户任务条目计数")
    @WrapUpResponseBody
    @RequestMapping(value = "/countUserTask/{userCode}", method = RequestMethod.GET)
    public Long countUserTask(@PathVariable String userCode) {
        return userTaskManager.countUserTask(userCode);
    }

    @ApiOperation(value = "用户任务条目计数", notes = "用户任务条目计数")
    @ApiImplicitParams({@ApiImplicitParam(name = "userCode", value = "操作用户"),
        @ApiImplicitParam(name = "osId", value = "应用代码"),
        @ApiImplicitParam(name = "optId", value = "业务代码"),
        @ApiImplicitParam(name = "optId", value = "业务方法、节点"),
        @ApiImplicitParam(name = "optId", value = "业务主键"),
        @ApiImplicitParam(name = "taskState", value = "活动状态（默认为A，不可修改） A:已分配 C:已完成 F:已委托给别人")
    })
    @WrapUpResponseBody
    @RequestMapping(value = "/countUserTask", method = RequestMethod.GET)
    public Long countUserTask(HttpServletRequest request) {
        Map<String, Object> filterMap = BaseController.collectRequestParameters(request);
        return userTaskManager.countUserTask(filterMap);
    }

    @ApiOperation(value = "根据userCode获取用户已完成任务条目计数", notes = "根据userCode获取用户已完成任务条目计数")
    @WrapUpResponseBody
    @RequestMapping(value = "/countUserCompleteTask/{userCode}", method = RequestMethod.GET)
    public Long countUserCompleteTask(@PathVariable String userCode) {
        return userTaskManager.countUserCompleteTask(userCode);
    }

    @ApiOperation(value = "用户已完成任务条目计数", notes = "用户已完成任务条目计数")
    @ApiImplicitParams({@ApiImplicitParam(name = "userCode", value = "操作用户"),
        @ApiImplicitParam(name = "osId", value = "应用代码"),
        @ApiImplicitParam(name = "optId", value = "业务代码"),
        @ApiImplicitParam(name = "optId", value = "业务方法、节点"),
        @ApiImplicitParam(name = "optId", value = "业务主键"),
        @ApiImplicitParam(name = "taskState", value = "活动状态（默认为C，不可修改） A:已分配 C:已完成 F:已委托给别人")
    })
    @WrapUpResponseBody
    @RequestMapping(value = "/countUserCompleteTask", method = RequestMethod.GET)
    public Long countUserCompleteTask(HttpServletRequest request) {
        Map<String, Object> filterMap = BaseController.collectRequestParameters(request);
        return userTaskManager.countUserCompleteTask(filterMap);
    }

    @ApiOperation(value = "鉴权:用户是否有权限操作任务", notes = "用户是否有权限操作任务")
    @WrapUpResponseBody
    @RequestMapping(value = "/checkTaskAuth/{taskId}/{userCode}", method = RequestMethod.GET)
    public boolean checkTaskAuth(@PathVariable String taskId, @PathVariable String userCode) {
        return userTaskManager.checkTaskAuth(taskId, userCode);
    }

    @ApiOperation(value = "完成任务", notes = "完成任务")
    @WrapUpResponseBody
    @RequestMapping(value = "/completeTask/{userCode}/{taskId}", method = RequestMethod.PUT)
    public void completeTask(@PathVariable String userCode, @PathVariable String taskId) {
        userTaskManager.completeTask(userCode, taskId);
    }


    @ApiOperation(value = "关闭用户任务", notes = "关闭用户任务")
    @WrapUpResponseBody
    @RequestMapping(value = "/closeTask/{userCode}/{taskId}", method = RequestMethod.PUT)
    public void closeTask(@PathVariable String userCode, @PathVariable String taskId) {
        userTaskManager.closeTask(userCode, taskId);
    }

    @ApiOperation(value = "关闭业务任务", notes = "关闭业务任务")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "osId", value = "应用代码"),
        @ApiImplicitParam(name = "optId", value = "业务代码"),
        @ApiImplicitParam(name = "optId", value = "业务方法、节点"),
        @ApiImplicitParam(name = "optId", value = "业务主键")
    })
    @WrapUpResponseBody
    @RequestMapping(value = "/closeOptTask", method = RequestMethod.PUT)
    public void closeOptTask(String osId, String optId, String optMethod, String optTag, HttpServletRequest request) {
        userTaskManager.closeOptTask(osId, optId, optMethod, optTag);
    }


    @ApiOperation(value = "转移任务", notes = "转移任务")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "taskId", value = "任务id"),
        @ApiImplicitParam(name = "userCode", value = "用户代码"),
        @ApiImplicitParam(name = "newOperator", value = "新的操作人员"),
        @ApiImplicitParam(name = "transDesc", value = "转移说明")
    })
    @WrapUpResponseBody
    @RequestMapping(value = "/transferTask", method = RequestMethod.POST)
    public void transferTask(String taskId, String userCode, String newOperator, String transDesc, HttpServletRequest request) {
        userTaskManager.transferTask(taskId, userCode, newOperator, transDesc);
    }

    @ApiOperation(value = "删除用户任务", notes = "删除用户任务")
    @WrapUpResponseBody
    @RequestMapping(value = "/{taskId}", method = RequestMethod.DELETE)
    public void deleteUserTaskById(@PathVariable String taskId) {
        userTaskManager.deleteUserTaskById(taskId);
    }

    @ApiOperation(value = "删除用户任务委托", notes = "删除用户任务委托")
    @WrapUpResponseBody
    @RequestMapping(value = "/deleteUserDepute/{relegateId}", method = RequestMethod.DELETE)
    public void deleteUserDepute(@PathVariable String relegateId) {
        userTaskManager.deleteUserDepute(relegateId);
    }

}
