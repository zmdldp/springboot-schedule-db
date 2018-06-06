package com.doowhop.schedule.domain;

import java.util.Date;

public class ScheduleTask {
	
	private Long taskId;

    private String taskName;

    private String taskMode;

    private String url;

    private String method;

	private String param;
    
    private String contentType;

    private String cron;

    private String initialDelay;

    private String fixedDelay;

    private String fixedRate;

    private String isImdExe;

    private String lastExecuteBegin;

    private String lastExecuteEnd;

    private String nextExecuteTime;

    private String status;

    private String procState;

    private String createUid;

    private Date createTime;

    private String lastModifyUid;

    private Date lastModifyTime;

	public Long getTaskId() {
		return taskId;
	}

	public void setTaskId(Long taskId) {
		this.taskId = taskId;
	}

	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public String getTaskMode() {
		return taskMode;
	}

	public void setTaskMode(String taskMode) {
		this.taskMode = taskMode;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	 public String getParam() {
			return param;
	}

	public void setParam(String param) {
		this.param = param;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public String getCron() {
		return cron;
	}

	public void setCron(String cron) {
		this.cron = cron;
	}

	public String getInitialDelay() {
		return initialDelay;
	}

	public void setInitialDelay(String initialDelay) {
		this.initialDelay = initialDelay;
	}

	public String getFixedDelay() {
		return fixedDelay;
	}

	public void setFixedDelay(String fixedDelay) {
		this.fixedDelay = fixedDelay;
	}

	public String getFixedRate() {
		return fixedRate;
	}

	public void setFixedRate(String fixedRate) {
		this.fixedRate = fixedRate;
	}

	public String getIsImdExe() {
		return isImdExe;
	}

	public void setIsImdExe(String isImdExe) {
		this.isImdExe = isImdExe;
	}

	public String getLastExecuteBegin() {
		return lastExecuteBegin;
	}

	public void setLastExecuteBegin(String lastExecuteBegin) {
		this.lastExecuteBegin = lastExecuteBegin;
	}

	public String getLastExecuteEnd() {
		return lastExecuteEnd;
	}

	public void setLastExecuteEnd(String lastExecuteEnd) {
		this.lastExecuteEnd = lastExecuteEnd;
	}

	public String getNextExecuteTime() {
		return nextExecuteTime;
	}

	public void setNextExecuteTime(String nextExecuteTime) {
		this.nextExecuteTime = nextExecuteTime;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getProcState() {
		return procState;
	}

	public void setProcState(String procState) {
		this.procState = procState;
	}

	public String getCreateUid() {
		return createUid;
	}

	public void setCreateUid(String createUid) {
		this.createUid = createUid;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getLastModifyUid() {
		return lastModifyUid;
	}

	public void setLastModifyUid(String lastModifyUid) {
		this.lastModifyUid = lastModifyUid;
	}

	public Date getLastModifyTime() {
		return lastModifyTime;
	}

	public void setLastModifyTime(Date lastModifyTime) {
		this.lastModifyTime = lastModifyTime;
	}
	
	
}
