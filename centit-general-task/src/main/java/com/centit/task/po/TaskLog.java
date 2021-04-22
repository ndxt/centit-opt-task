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
@Table(name = "F_TASK_LOG")
public class TaskLog implements java.io.Serializable {
    private static final long serialVersionUID =  1L;

    @Id
    @Column(name = "LOG_ID")
    @ValueGenerator(strategy = GeneratorType.UUID22)
    @Length(max = 32, message = "字段长度不能大于{max}")
    private String logId;

    @Column(name = "TASK_ID")
    @Length(max = 32, message = "字段长度不能大于{max}")
    private String taskId;

    @Column(name="LOG_CONTENT")
    @Length(max = 2048, message = "字段长度不能大于{max}")
    private String logContent;

    @Length(max = 1, message = "字段长度不能大于{max}")
    @Column(name="LOG_TYPE")
    private Date logType;

    @Column(name="LOG_TIME")
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
     * 实际 工作量，小時
     */
    @Column(name="WORKLOAD_HOURS")
    private Date workloadHours;

}
