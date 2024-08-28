package com.project.login.common;

import java.util.Arrays;
import java.util.List;

public enum PermitAllUrl {
	
	PermitURL(Arrays.asList("/", "/sign-in", "/error", "/favicon.ico", "/sign-up","/health/check", "/login",  "/sign-up-page"));
	
	private List<String> permitURL;
	
	private PermitAllUrl(List<String> permitURL) {
		this.permitURL = permitURL;
	}
	
	public String[] getPermitURL() {
		return permitURL.toArray(new String[0]);
	}
	
	
}
