package com.project.login.common;

import lombok.Getter;

@Getter
public enum Role {
	
	NORMAL("일반유저"),
	ADMIN("관리자");
	
	private String desc;
	
	private Role(String desc) {
		this.desc = desc;
	}
}
