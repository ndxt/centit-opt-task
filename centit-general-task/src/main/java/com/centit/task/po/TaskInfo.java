package com.centit.task.po;

import com.centit.framework.core.dao.DictionaryMap;
import com.centit.support.database.orm.GeneratorType;
import com.centit.support.database.orm.ValueGenerator;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Data
@Entity
@Table(name = "F_TASK_INFO")
public class TaskInfo implements java.io.Serializable {
    private static final long serialVersionUID =  1L;

    @Id
    @Column(name = "TASK_ID")
    @ValueGenerator(strategy = GeneratorType.UUID22)
    @Length(max = 32, message = "字段长度不能大于{max}")
    private String taskId;

    @Column(name="TASK_TITLE")
    @Length(max = 100, message = "字段长度不能大于{max}")
    private String taskTitle;

    @Column(name="TASK_CONTENT")
    @Length(max = 2048, message = "字段长度不能大于{max}")
    private String taskContent;

    @Column(name = "TASK_OFFICER")
    @DictionaryMap(value = "userCode", fieldName = "officerName")
    @Length(max = 32, message = "字段长度不能大于{max}")
    private String taskOfficer;

    @Column(name = "TASK_REPORTER")
    @DictionaryMap(value = "userCode", fieldName = "reporterName")
    @Length(max = 32, message = "字段长度不能大于{max}")
    private String taskReporter;

    @Column(name="ASSIGN_TIME")
    private Date assignTime;

    /**
     * 评估 工作量，小時
     */
    @Column(name="WORKLOAD_HOURS")
    private Date workloadHours;
    /**
     * 最后期限
     */
    @Column(name="DEADLINE_TIME")
    private Date deadlineTime;

    @Column(name = "UNIT_CODE")
    @DictionaryMap(value = "unitCode", fieldName = "unitName")
    @Length(max = 32, message = "字段长度不能大于{max}")
    private String unitCode;

    @Column(name = "USER_CODE")
    @DictionaryMap(value = "userCode", fieldName = "userName")
    @Length(max = 32, message = "字段长度不能大于{max}")
    private String userCode;


    /**
    * 等同于 wf_opt_info中的 APPLICATION_ID
     * 对应应用系统
     * 比如 工作流引擎 workflow ， 考勤系统id
    */
    @Column(name = "OS_ID")
    @Length(max = 32, message = "字段长度不能大于{max}")
    private String osId;

    /**
    *功能模块 对应工作流中的 流程代码， 考勤系统中的功能*/
    @Column(name="OPT_ID")
    @Length(max = 64, message = "字段长度不能大于{max}")
    private String optId;

    /**
    *操作方法 对应工作流中的 节点 */
    @Column(name="OPT_METHOD")
    @Length(max = 64, message = "字段长度不能大于{max}")
    private String optMethod;

}
