package com.centit.task.po;

import com.centit.framework.core.dao.DictionaryMap;
import com.centit.support.database.orm.GeneratorType;
import com.centit.support.database.orm.ValueGenerator;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * 任务信息表
 */
@Data
@Entity
@Table(name = "F_TASK_INFO")
public class TaskInfo implements java.io.Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "TASK_ID")
    @ValueGenerator(strategy = GeneratorType.UUID22)
    @Length(max = 32, message = "字段长度不能大于{max}")
    @ApiModelProperty("任务编号")
    private String taskId;

    @Column(name = "TASK_TITLE")
    @Length(max = 100, message = "字段长度不能大于{max}")
    @ApiModelProperty("任务标题")
    private String taskTitle;

    @Column(name = "TASK_CONTENT")
    @Length(max = 2048, message = "字段长度不能大于{max}")
    @ApiModelProperty("任务内容")
    private String taskContent;

    @Column(name = "TASK_OFFICER")
    @DictionaryMap(value = "userCode", fieldName = "officerName")
    @Length(max = 32, message = "字段长度不能大于{max}")
    @ApiModelProperty("任务分配人")
    private String taskOfficer;

    @Column(name = "TASK_REPORTER")
    @DictionaryMap(value = "userCode", fieldName = "reporterName")
    @Length(max = 32, message = "字段长度不能大于{max}")
    @ApiModelProperty("任务报告人")
    private String taskReporter;

    @Column(name = "ASSIGN_TIME")
    @ApiModelProperty("分配时间")
    private Date assignTime;

    /**
     * 评估 工作量，小時
     */
    @Column(name = "WORKLOAD_HOURS")
    @ApiModelProperty("评估 工作量，小時")
    private Date workloadHours;
    /**
     * 最后期限
     */
    @Column(name = "DEADLINE_TIME")
    @ApiModelProperty("最后期限")
    private Date deadlineTime;

    @Column(name = "UNIT_CODE")
    @DictionaryMap(value = "unitCode", fieldName = "unitName")
    @Length(max = 32, message = "字段长度不能大于{max}")
    @ApiModelProperty("操作机构")
    private String unitCode;

    @Column(name = "USER_CODE")
    @DictionaryMap(value = "userCode", fieldName = "userName")
    @Length(max = 32, message = "字段长度不能大于{max}")
    @ApiModelProperty("操作用户")
    private String userCode;

    /**
     * 等同于 wf_opt_info中的 APPLICATION_ID
     * 对应应用系统
     * 比如 工作流引擎 workflow ， 考勤系统id
     */
    @Column(name = "OS_ID")
    @Length(max = 32, message = "字段长度不能大于{max}")
    @ApiModelProperty("业务系统id:等同于APPLICATION_ID,对应应用系统，比如工作流引擎workflow、考勤系统id")
    private String osId;

    @Column(name = "OPT_ID")
    @Length(max = 64, message = "字段长度不能大于{max}")
    @ApiModelProperty("功能模块:对应工作流中的流程代码，考勤系统中的功能")
    private String optId;

    @Column(name = "OPT_METHOD")
    @Length(max = 64, message = "字段长度不能大于{max}")
    @ApiModelProperty("操作方法:对应工作流中的节点")
    private String optMethod;

}
