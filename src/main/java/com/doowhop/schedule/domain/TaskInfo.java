package com.doowhop.schedule.domain;

public class TaskInfo {
	
	private long taskId;
	
	private String taskName;
	
	private String taskType;

	private String url;
	
	private String method;
	
	private String postParam;
	
	private String cron;
	
	private String initialDelay;
	
	private String fixedDelay;
	
	private String fixedRate;
	
	private String isImmediExe;
	
	private String timeZone;
	
	private String nextExecute;
	
	private String lastExecute;
	
	private String state;
	
	private String createUid;
	
	private long createTime;
	
	private String lastModifyUid;
	
	private long lastModifyTime;
	

	public long getTaskId() {
		return taskId;
	}

	public void setTaskId(long taskId) {
		this.taskId = taskId;
	}

	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public String getTaskType() {
		return taskType;
	}

	public void setTaskType(String taskType) {
		this.taskType = taskType;
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

	public String getPostParam() {
		return postParam;
	}

	public void setPostParam(String postParam) {
		this.postParam = postParam;
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

	public String getIsImmediExe() {
		return isImmediExe;
	}

	public void setIsImmediExe(String isImmediExe) {
		this.isImmediExe = isImmediExe;
	}

	public String getTimeZone() {
		return timeZone;
	}

	public void setTimeZone(String timeZone) {
		this.timeZone = timeZone;
	}

	public String getNextExecute() {
		return nextExecute;
	}

	public void setNextExecute(String nextExecute) {
		this.nextExecute = nextExecute;
	}

	public String getLastExecute() {
		return lastExecute;
	}

	public void setLastExecute(String lastExecute) {
		this.lastExecute = lastExecute;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getCreateUid() {
		return createUid;
	}

	public void setCreateUid(String createUid) {
		this.createUid = createUid;
	}

	public long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(long createTime) {
		this.createTime = createTime;
	}

	public String getLastModifyUid() {
		return lastModifyUid;
	}

	public void setLastModifyUid(String lastModifyUid) {
		this.lastModifyUid = lastModifyUid;
	}

	public long getLastModifyTime() {
		return lastModifyTime;
	}

	public void setLastModifyTime(long lastModifyTime) {
		this.lastModifyTime = lastModifyTime;
	}
	
}
