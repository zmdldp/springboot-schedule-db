CREATE TABLE schedule_task (
	record_no varchar(30) NOT NULL, 
	schedule_name varchar(30) COMMENT '调度名称', 
    schedule_category varchar(2) COMMENT '调度分类：0，采集调度；1，分析调度'
	schedule_type varchar(2) COMMENT '调度任务类型：采集调度(0)--001，受理直联商户采集；002，受理间联数据采集；003，收银宝商户采集；004，收银宝风险采集 / 分析调度(1)--101... ', 
	cron varchar(20) COMMENT 'cron表达式', 
	last_execute_start varchar(20) COMMENT '上次调度开始时间 yyyyMMdd HHmmss', 
	last_execute_end varchar(20) COMMENT '上次调度结束时间 yyyyMMdd HHmmss', 
	next_execute_time varchar(20) COMMENT '下次调度开始时间 yyyyMMdd HHmmss', 
	status char(1) COMMENT '调度状态 0-移除(暂停) 1-正常', 
	proc_state char(2) COMMENT '调度执行状态 00-执行完成(等待执行) 01-执行中',
	create_uid varchar(30) COMMENT '录入人', 
	create_time bigint COMMENT '录入时间', 
	last_modify_uid varchar(30) COMMENT '最后修改人', 
	last_modify_time bigint COMMENT '最后修改时间', 
	PRIMARY KEY (record_no)
	) 
	ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='调度任务表';
