package com.centit.task.po;

import com.centit.support.database.orm.GeneratorType;
import com.centit.support.database.orm.ValueGenerator;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;

@Data
public class UserTaskInfo implements java.io.Serializable {
    private static final long serialVersionUID =  1L;

    @Id
    @Column(name = "TASK_ID")
    @ValueGenerator(strategy = GeneratorType.UUID22)
    private String taskId;

}
