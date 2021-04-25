package com.centit.task.po;

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
 * create by scaffold
 * 任务转移，直接修该 UserTaskInfo ,这儿值记录日志
 * @author codefan@hotmail.com
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "F_TASK_TRANSFER")
public class TaskTransfer implements java.io.Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "TRANSFER_ID")
    @Length(max = 32, message = "字段长度不能大于{max}")
    @ValueGenerator(strategy = GeneratorType.UUID22)
    private String transferId;
    //委托人
    @Length(max = 32, message = "字段长度不能大于{max}")
    @Column(name = "GRANTOR")
    private String grantor;

    //被委托人
    @Length(max = 32, message = "字段长度不能大于{max}")
    @Column(name = "GRANTEE")
    private String grantee;

    @Length(max = 256, message = "字段长度不能大于{max}")
    @Column(name = "GRANT_DESC")
    private String grantDesc;

    //记录人
    @Length(max = 32, message = "字段长度不能大于{max}")
    @Column(name = "RECORDER")
    private String recorder;

    @Column(name = "RECORD_DATE")
    private Date recordDate;


}
