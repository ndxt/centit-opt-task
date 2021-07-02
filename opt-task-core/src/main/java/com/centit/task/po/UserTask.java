package com.centit.task.po;

import com.centit.framework.core.dao.DictionaryMap;
import com.centit.support.database.orm.GeneratorType;
import com.centit.support.database.orm.ValueGenerator;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * 用户任务表
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "F_USER_TASK")
public class UserTask implements java.io.Serializable {
    private static final long serialVersionUID = 1L;

    public UserTask(String osId) {
        this.osId = osId;
    }

    public static UserTask create(){
        return new UserTask();
    }

    public static UserTask createWF(){
        return new UserTask("workflow");
    }

    @Id
    @Column(name = "TASK_ID")
    @ValueGenerator(strategy = GeneratorType.UUID22)
    @Length(max = 32, message = "字段长度不能大于{max}")
    private String taskId;

    /**
     * 等同于 wf_opt_info中的 APPLICATION_ID
     * 对应应用系统
     * 比如 工作流引擎 workflow ， 考勤系统id
     */
    @Column(name = "OS_ID")
    @Length(max = 32, message = "字段长度不能大于{max}")
    private String osId;

    /**
     * 功能模块 对应工作流中的 流程代码， 考勤系统中的功能
     */
    @Column(name = "OPT_ID")
    @Length(max = 64, message = "字段长度不能大于{max}")
    private String optId;

    /**
     * 操作方法 对应工作流中的 节点
     */
    @Column(name = "OPT_METHOD")
    @Length(max = 64, message = "字段长度不能大于{max}")
    private String optMethod;

    /**
     * 操作业务标记 对应工作流中的 nodeInstId，考勤业务id
     */
    @Column(name = "OPT_TAG")
    @Length(max = 200, message = "字段长度不能大于{max}")
    private String optTag;

    /**
     * 对应的业务名称
     */
    @Column(name = "OPT_NAME")
    @Length(max = 400, message = "字段长度不能大于{max}")
    private String optName;

    @Column(name = "ASSIGN_TIME")
    private Date assignTime;

    @Column(name = "UPDATE_TIME")
    private Date updateTime;

    @Column(name = "EXPIRE_TIME")
    private Date expireTime;

    @Column(name = "UNIT_CODE")
    @DictionaryMap(value = "unitCode", fieldName = "unitName")
    @Length(max = 32, message = "字段长度不能大于{max}")
    private String unitCode;

    @Column(name = "USER_CODE")
    @DictionaryMap(value = "userCode", fieldName = "userName")
    @Length(max = 32, message = "字段长度不能大于{max}")
    private String userCode;

    @Length(max = 32, message = "字段长度不能大于{max}")
    @Column(name = "ROLE_TYPE")
    private String roleType;

    @Length(max = 32, message = "字段长度不能大于{max}")
    @Column(name = "ROLE_CODE")
    private String roleCode;

    public static final String TASK_STATE_ALLOCATED = "A";
    public static final String TASK_STATE_COMPLETED = "C";
    public static final String TASK_STATE_ENTRUST = "E";
    public static final String TASK_STATE_TRANSFER = "T";
    public static final String TASK_STATE_CLOSE = "F";
    /**
     * 任务状态 A:已分配 C:已完成 E:已委托给别人 T:已转移 F:已关闭',
     */
    @Length(max = 1, message = "字段长度不能大于{max}")
    @Column(name = "TASK_STATE")
    private String taskState;

    @Column(name = "AUTH_DESC")
    @Length(max = 500, message = "字段长度不能大于{max}")
    private String authDesc;

    /**
     * 页面Url varchar 200
     */
    @Column(name = "OPT_PAGE_URL")
    @Length(max = 256, message = "字段长度不能大于{max}")
    private String optPageUrl;

    @Column(name = "REQUEST_PARAMS")
    @Length(max = 500, message = "字段长度不能大于{max}")
    private String requestParams;

}
