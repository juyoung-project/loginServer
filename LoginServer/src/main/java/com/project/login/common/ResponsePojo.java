package com.project.login.common;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResponsePojo<T> {
	
	private T data;
	private String msg;
	private Code code;
	
	public static <T> ResponsePojo<T> success(T data, String msg) {
		return createResponse(Code.SUCCESS, data, msg);
	}
	
	public static <T> ResponsePojo<T> error(T data, String msg) {
		return createResponse(Code.ERROR, data, msg);
	}
	private static <T> ResponsePojo<T> createResponse(Code code, T data, String msg) {
		return new ResponsePojo<T>(code, data, msg);
	}
	
	private ResponsePojo(Code code, T data, String msg) {
		this.code = code;
		this.data = data;
		this.msg = msg;
	}

}
