package com.doowhop.schedule.enums;

public enum ContentTypeEnum {
	
	JSON("01","application/json"),
	URLENCODED("02","application/x-www-form-urlencoded");	
	   
	private String type;
	private String name;
	
	ContentTypeEnum(String type,String name) {
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
	
	
	public static ContentTypeEnum getContentTypeEnum(String value) {
		if (value != null) {
			for (ContentTypeEnum taskEnum : values()) {
				if (taskEnum.getType().equals(value)) {
					return taskEnum;
				}
			}
		}
		return null;
	}

}
