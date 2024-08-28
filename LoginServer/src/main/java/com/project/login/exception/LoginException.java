package com.project.login.exception;

import com.project.login.common.Code;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginException extends RuntimeException{

	private static final long serialVersionUID = 1L;
	
	private Code code;
	private String customErrorStr;
	
	public LoginException(  Code code ) {
		super(code.getDescMsg());
		this.code = code;
	}
	
	public LoginException ( Code code, String customErrorStr ) {
		super(customErrorStr);
		this.code = code;
		this.customErrorStr = customErrorStr;
	}
	
}
