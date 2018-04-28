package com.doowhop.schedule.enums;

public enum TaskTypeEnum {
	
	CRON_TASK("01","cronTask"),
	FIXED_RATE_TASK("02","fixedRateTask"),	
	FIXED_DELAY_TASK("03","fixedDelayTask"),
	TRIGGER_TASK("04","triggerTask");
    
	private String type;
	private String name;
	
	TaskTypeEnum(String type,String name) {
		this.type = type;
		this.name = name;
	}
	
	
	public String getType() {
		return type;
	}


	public void setType(String type) {
		this.type = type;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}
	
	
	public static TaskTypeEnum getReconTaskEnum(String value) {
		if (value != null) {
			for (TaskTypeEnum taskEnum : values()) {
				if (taskEnum.getType().equals(value)) {
					return taskEnum;
				}
			}
		}
		return null;
	}

}
