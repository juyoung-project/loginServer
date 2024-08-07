package com.project.login.common;

import java.util.Map;

public class ResponsePojo {
	
	private Map<String, Object> data;
	private String msg;
	private Code code;
	
	public static ResponsePojo success(Map<String, Object> data, String msg) {
		return buildMessage(Code.SUCCESS, data, msg);
	}
	
	public static ResponsePojo error(Map<String, Object> data, String msg) {
		return buildMessage(Code.ERROR, data, msg);
	}
	private static ResponsePojo buildMessage(Code code, Map<String, Object> data, String msg) {
		ResponsePojo res = new ResponsePojo(code, data, msg);
		return res;
	}
	
	private ResponsePojo(Code code, Map<String, Object> data, String msg) {
		this.code = code;
		this.data = data;
		this.msg = msg;
	}
	
	
	
}
