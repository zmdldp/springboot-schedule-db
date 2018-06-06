package com.doowhop.schedule.enums;

public enum TaskModeEnum {
	
	CRON_TASK("01","cronTask"),
	FIXED_RATE_TASK("02","fixedRateTask"),	
	FIXED_DELAY_TASK("03","fixedDelayTask"),
	TRIGGER_TASK("04","triggerTask");
    
	private String mode;
	private String name;
	
	TaskModeEnum(String mode,String name) {
		this.mode = mode;
		this.name = name;
	}
	
	
	public String getMode() {
		return mode;
	}


	public void setType(String mode) {
		this.mode = mode;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}
	
	
	public static TaskModeEnum getTaskModeEnum(String value) {
		if (value != null) {
			for (TaskModeEnum taskEnum : values()) {
				if (taskEnum.getMode().equals(value)) {
					return taskEnum;
				}
			}
		}
		return null;
	}

}
