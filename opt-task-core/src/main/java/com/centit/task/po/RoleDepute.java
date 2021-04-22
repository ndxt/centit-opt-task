package com.centit.task.po;

import com.alibaba.fastjson.annotation.JSONField;
import com.centit.support.database.orm.GeneratorType;
import com.centit.support.database.orm.ValueGenerator;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * create by scaffold
 * 行政角色 委托信息
 * @author codefan@hotmail.com
 */
@Data
@Entity
@Table(name = "F_ROLE_DEPUTE")
public class RoleDepute implements java.io.Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "RELEGATE_ID")
    @ValueGenerator(strategy = GeneratorType.UUID22)
    @Length(max = 32, message = "字段长度不能大于{max}")
    private String relegateId;
    //委托人
    @Length(max = 32, message = "字段长度不能大于{max}")
    @Column(name = "GRANTOR")
    private String grantor;
    //被委托人
    @Length(max = 32, message = "字段长度不能大于{max}")
    @Column(name = "GRANTEE")
    private String grantee;

    @Length(max = 1, message = "字段长度不能大于{max}")
    @Column(name = "IS_VALID")
    private boolean isValid;

    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    @Column(name = "RELEGATE_TIME")
    private Date relegateTime;

    //失效时间
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    @Column(name = "EXPIRE_TIME")
    private Date expireTime;

    @Length(max = 32, message = "字段长度不能大于{max}")
    @Column(name = "UNIT_CODE")
    private String unitCode;
    /**
     * 目前只能是 行政职务 “XZ”
     */
    @Length(max = 32, message = "字段长度不能大于{max}")
    @Column(name = "ROLE_TYPE")
    private String roleType;

    @Length(max = 32, message = "字段长度不能大于{max}")
    @Column(name = "ROLE_CODE")
    private String roleCode;

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
