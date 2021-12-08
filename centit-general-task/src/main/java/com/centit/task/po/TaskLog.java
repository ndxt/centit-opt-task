package com.centit.task.po;

import com.centit.framework.core.dao.DictionaryMap;
import com.centit.support.database.orm.GeneratorType;
import com.centit.support.database.orm.ValueGenerator;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import java.util.Date;

/**
 * 任务日志表
 */
@Data
@Entity
@Table(name = "F_TASK_LOG")
public class TaskLog implements java.io.Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "LOG_ID")
    @ValueGenerator(strategy = GeneratorType.UUID22)
    @Length(max = 32, message = "字段长度不能大于{max}")
    private String logId;

    @Column(name = "TASK_ID")
    @Length(max = 32, message = "字段长度不能大于{max}")
    private String taskId;

    @Column(name = "LOG_CONTENT")
    @Length(max = 2048, message = "字段长度不能大于{max}")
    private String logContent;

    @Length(max = 1, message = "字段长度不能大于{max}")
    @Column(name = "LOG_TYPE")
    @ApiModelProperty("日志类型，M:备注，R:日志")
    private String logType;

    @Column(name = "LOG_TIME")
    @Temporal(TemporalType.TIMESTAMP)
    @ValueGenerator( strategy= GeneratorType.FUNCTION, value = "today()")
    private Date logTime;

    @Column(name = "UNIT_CODE")
    @DictionaryMap(value = "unitCode", fieldName = "unitName")
    @Length(max = 32, message = "字段长度不能大于{max}")
    private String unitCode;

    @Column(name = "USER_CODE")
    @DictionaryMap(value = "userCode", fieldName = "userName")
    @Length(max = 32, message = "字段长度不能大于{max}")
    private String userCode;

    /**
     * 实际 工作量，分钟
     */
    @Column(name = "WORKLOAD")
    @ApiModelProperty("本次工作耗时，单位分钟")
    private Long workload;

}
