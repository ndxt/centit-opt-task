package com.centit.task.test;

import com.alibaba.fastjson2.JSON;
import com.centit.support.algorithm.DatetimeOpt;
import com.centit.support.algorithm.UuidOpt;
import com.centit.task.po.TaskInfo;

public class TestTaskInfo {
    public static void main(String[] args) {
        TaskInfo taskInfo = new TaskInfo();
        taskInfo.setTaskId(UuidOpt.getUuidAsString22());
        taskInfo.setOsId("mZqlFCU1Q2iIVAK7_-04rg");
        taskInfo.setTaskReporter("U5iJtAdt");
        taskInfo.setTaskClass("A");
        taskInfo.setTaskOfficer("U5iJtAdt");
        taskInfo.setAssignTime(DatetimeOpt.currentUtilDate());
        taskInfo.setDeadlineTime(DatetimeOpt.addDays(DatetimeOpt.currentUtilDate(), 20));
        taskInfo.setTaskState("A");
        taskInfo.setTaskTitle("设计任务");
        taskInfo.setTaskContent("根据需求说明书设计界面");

        System.out.println(JSON.toJSONString(taskInfo));
    }
}
