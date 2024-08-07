package com.project.login.common;

public enum Code {
	
	SUCCESS("200", "응답 성공"),
	ERROR("900", "의도적인 응답실패"),
	TOKEN_EXPIRED("800", "JWT 토큰만료");
	
	private String code;
	private String resMsg;
	
	private Code(String code, String resMsg) {
		this.code = code;
		this.resMsg = resMsg;
	}
	
}
