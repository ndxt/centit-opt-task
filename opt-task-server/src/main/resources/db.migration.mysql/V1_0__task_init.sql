-- task
CREATE TABLE F_USER_TASK  (
  TASK_ID  varchar(32)  NOT NULL COMMENT '任务编号',
  OS_ID    varchar(32)  NOT NULL COMMENT '业务系统id:等同于APPLICATION_ID,对应应用系统，比如工作流引擎workflow、考勤系统id',
  OPT_ID   varchar(64)  DEFAULT NULL COMMENT '业务模块:对应工作流中的流程代码，考勤系统中的功能',
  OPT_NAME varchar(400)  DEFAULT NULL COMMENT '业务名称',
  OPT_METHOD varchar(64)  DEFAULT NULL COMMENT '操作方法:对应工作流中的节点',
  OPT_TAG  varchar(200)  DEFAULT NULL COMMENT '操作业务标记:对应工作流中的 nodeInstId，考勤业务id',
  ASSIGN_TIME datetime(0) DEFAULT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '分配时间',
  UPDATE_TIME datetime(0) DEFAULT NULL COMMENT '更新时间',
  EXPIRE_TIME datetime(0) DEFAULT NULL COMMENT '过期时间',
  UNIT_CODE varchar(32)  DEFAULT NULL COMMENT '操作机构',
  USER_CODE varchar(32)  DEFAULT NULL COMMENT '操作用户',
  ROLE_TYPE varchar(32)  DEFAULT NULL COMMENT '角色类别',
  ROLE_CODE varchar(32)  DEFAULT NULL COMMENT '角色代码',
  TASK_STATE char(1)  DEFAULT NULL COMMENT '任务状态 A:已分配 C:已完成 E:已委托给别人 T:已转移 F:已关闭',
  AUTH_DESC varchar(500)  DEFAULT NULL COMMENT '授权说明',
  OPT_PAGE_URL varchar(256)  DEFAULT NULL COMMENT '页面url',
  REQUEST_PARAMS varchar(500)  DEFAULT NULL COMMENT '请求参数',
  PRIMARY KEY (TASK_ID) 
);
ALTER TABLE F_USER_TASK         comment '用户任务表';


CREATE TABLE F_TASK_TRANSFER  (
  TRANSFER_ID  varchar(32)  NOT NULL COMMENT '任务转移编号',
  GRANTOR   varchar(32)  DEFAULT NULL COMMENT '委托人',
  GRANTEE   varchar(32)  DEFAULT NULL COMMENT '被委托人',
  RECORDER  varchar(32)  DEFAULT NULL COMMENT '记录人',
  GRANT_DESC varchar(256)  DEFAULT NULL COMMENT '说明',
  RECORD_DATE datetime(0) NOT NULL COMMENT '记录时间',
  PRIMARY KEY (TRANSFER_ID)
);
ALTER TABLE F_TASK_TRANSFER         comment '任务转移表';


CREATE TABLE F_ROLE_DEPUTE  (
  TRANSFER_ID  varchar(32)  NOT NULL COMMENT '任务转移编号',
  GRANTOR   varchar(32)  DEFAULT NULL COMMENT '委托人',
  GRANTEE   varchar(32)  DEFAULT NULL COMMENT '被委托人',
  RECORDER  varchar(32)  DEFAULT NULL COMMENT '记录人',
  GRANT_DESC varchar(256)  DEFAULT NULL COMMENT '说明',
  RECORD_DATE datetime(0) NOT NULL COMMENT '记录时间',
  PRIMARY KEY (TRANSFER_ID)
);
ALTER TABLE F_ROLE_DEPUTE         comment '任务(角色)委托表';


-- general task
CREATE TABLE F_TASK_INFO  (
  TASK_ID  varchar(32)  NOT NULL COMMENT '任务编号',
  TASK_TITLE varchar(100)  DEFAULT NULL COMMENT '任务标题',
  TASK_CONTENT varchar(2048)  DEFAULT NULL COMMENT '任务内容',
  TASK_OFFICER varchar(32)  DEFAULT NULL COMMENT '任务分配人',
  TASK_REPORTER varchar(32)  DEFAULT NULL COMMENT '任务报告人',
  ASSIGN_TIME    datetime(0) NOT NULL COMMENT '分配时间',
  WORKLOAD bigint(20) NOT NULL COMMENT '评估 工作量，分钟',
  DEADLINE_TIME  datetime(0) NOT NULL COMMENT '最后期限',
  UNIT_CODE varchar(32)  DEFAULT NULL COMMENT '操作机构',
  USER_CODE varchar(32)  DEFAULT NULL COMMENT '操作用户',
  OS_ID    varchar(32)  NOT NULL COMMENT '业务系统id:等同于APPLICATION_ID,对应应用系统，比如工作流引擎workflow、考勤系统id',
  OPT_ID   varchar(64)  DEFAULT NULL COMMENT '业务模块:对应工作流中的流程代码，考勤系统中的功能',
  OPT_METHOD varchar(64)  DEFAULT NULL COMMENT '操作方法:对应工作流中的节点',
  PRIMARY KEY (TASK_ID)
);
ALTER TABLE F_TASK_INFO         comment '任务信息表';

CREATE TABLE F_TASK_LOG  (
  LOG_ID   varchar(32)  NOT NULL COMMENT '任务日志编号',
  TASK_ID  varchar(32)  NOT NULL COMMENT '任务编号',
  LOG_CONTENT varchar(2048)  DEFAULT NULL COMMENT '日志内容',
  LOG_TYPE    char(1)  DEFAULT NULL COMMENT '日志类型',
  LOG_TIME    datetime(0) NOT NULL COMMENT '记录时间',
  UNIT_CODE varchar(32)  DEFAULT NULL COMMENT '操作机构',
  USER_CODE varchar(32)  DEFAULT NULL COMMENT '操作用户',
  WORKLOAD bigint(20) NOT NULL COMMENT '实际 工作量，分钟',
  PRIMARY KEY (LOG_ID)
);
ALTER TABLE F_TASK_LOG         comment '任务日志表';