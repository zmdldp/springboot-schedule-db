package com.doowhop.schedule.enums;

public enum RetCodeEnum {
	
	SUCCESS("000000","成功"),
	FAILED("000002","失败"),	
	UNKNOWN("999999","未知异常");
    
	private String type;
	private String name;
	
	RetCodeEnum(String type,String name) {
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
	
	
	public static RetCodeEnum getRetCodeEnum(String value) {
		if (value != null) {
			for (RetCodeEnum retCodeEnum : values()) {
				if (retCodeEnum.getType().equals(value)) {
					return retCodeEnum;
				}
			}
		}
		return null;
	}

}
