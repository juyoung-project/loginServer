package com.project.login.common;

import lombok.Getter;

@Getter
public enum Code {
	
	SUCCESS("200", "응답 성공", "정상응답"),
	ERROR("900", "의도적인 응답실패", "의도적인 응답실패"),
	TOKEN_EXPIRED("800", "JWT 토큰만료", "토큰만료");
	
	
	private String code;
	private String resMsg;
	private String descMsg;
	
	private Code(String code, String resMsg, String descMsg) {
		this.code = code;
		this.resMsg = resMsg;
		this.descMsg = descMsg;
	}
	
}
