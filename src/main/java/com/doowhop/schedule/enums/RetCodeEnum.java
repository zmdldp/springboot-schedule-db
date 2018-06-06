package com.doowhop.schedule.enums;

public enum RetCodeEnum {
	
	SUCCESS("000000","处理成功"),
	FAILED("000002","处理失败"),	
	UNKNOWN("999999","未知异常");
    
	private String code;
	private String desc;
	
	RetCodeEnum(String code,String desc) {
		this.code = code;
		this.desc = desc;
	}
	
	
	public String getCode() {
		return code;
	}


	public void setCode(String code) {
		this.code = code;
	}


	public String getDesc() {
		return desc;
	}


	public void setDesc(String desc) {
		this.desc = desc;
	}
	
	
	public static RetCodeEnum getRetCodeEnum(String value) {
		if (value != null) {
			for (RetCodeEnum retCodeEnum : values()) {
				if (retCodeEnum.getCode().equals(value)) {
					return retCodeEnum;
				}
			}
		}
		return null;
	}

}
