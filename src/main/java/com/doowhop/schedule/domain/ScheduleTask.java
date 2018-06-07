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

    private Date lastExecuteBegin;

    private Date lastExecuteEnd;

    private Date nextExecuteTime;

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
        this.taskName = taskName == null ? null : taskName.trim();
    }

    public String getTaskMode() {
        return taskMode;
    }

    public void setTaskMode(String taskMode) {
        this.taskMode = taskMode == null ? null : taskMode.trim();
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url == null ? null : url.trim();
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method == null ? null : method.trim();
    }

    public String getParam() {
        return param;
    }

    public void setParam(String param) {
        this.param = param == null ? null : param.trim();
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType == null ? null : contentType.trim();
    }

    public String getCron() {
        return cron;
    }

    public void setCron(String cron) {
        this.cron = cron == null ? null : cron.trim();
    }

    public String getInitialDelay() {
        return initialDelay;
    }

    public void setInitialDelay(String initialDelay) {
        this.initialDelay = initialDelay == null ? null : initialDelay.trim();
    }

    public String getFixedDelay() {
        return fixedDelay;
    }

    public void setFixedDelay(String fixedDelay) {
        this.fixedDelay = fixedDelay == null ? null : fixedDelay.trim();
    }

    public String getFixedRate() {
        return fixedRate;
    }

    public void setFixedRate(String fixedRate) {
        this.fixedRate = fixedRate == null ? null : fixedRate.trim();
    }

    public String getIsImdExe() {
        return isImdExe;
    }

    public void setIsImdExe(String isImdExe) {
        this.isImdExe = isImdExe == null ? null : isImdExe.trim();
    }

    public Date getLastExecuteBegin() {
        return lastExecuteBegin;
    }

    public void setLastExecuteBegin(Date lastExecuteBegin) {
        this.lastExecuteBegin = lastExecuteBegin;
    }

    public Date getLastExecuteEnd() {
        return lastExecuteEnd;
    }

    public void setLastExecuteEnd(Date lastExecuteEnd) {
        this.lastExecuteEnd = lastExecuteEnd;
    }

    public Date getNextExecuteTime() {
        return nextExecuteTime;
    }

    public void setNextExecuteTime(Date nextExecuteTime) {
        this.nextExecuteTime = nextExecuteTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public String getProcState() {
        return procState;
    }

    public void setProcState(String procState) {
        this.procState = procState == null ? null : procState.trim();
    }

    public String getCreateUid() {
        return createUid;
    }

    public void setCreateUid(String createUid) {
        this.createUid = createUid == null ? null : createUid.trim();
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
        this.lastModifyUid = lastModifyUid == null ? null : lastModifyUid.trim();
    }

    public Date getLastModifyTime() {
        return lastModifyTime;
    }

    public void setLastModifyTime(Date lastModifyTime) {
        this.lastModifyTime = lastModifyTime;
    }
}
