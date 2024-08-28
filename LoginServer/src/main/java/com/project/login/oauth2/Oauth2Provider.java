package com.project.login.oauth2;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;

import com.project.login.repository.Member;


public enum Oauth2Provider {

	GITHUB("github", (attributes) -> {
		return Member.builder()
				.name(String.valueOf(attributes.get("name")))
				.email(String.valueOf(attributes.get("email"))).build();
	}),
	NAVER("naver", (attributes) -> {
		Map<String, Object> jsonMap = (Map<String, Object>) attributes.get("response");
		return Member.builder()
				.name(String.valueOf(jsonMap.get("name")))
				.email(String.valueOf(jsonMap.get("email"))).build();
	}),
	;

	private final String registrationId;
	private final Function<Map<String, Object>, Member> of;

	Oauth2Provider(String registrationId, Function<Map<String, Object>, Member> of) {
		this.registrationId = registrationId;
		this.of = of;
	}
	
	public static Member getOauthInfo(String registrationId, Map<String, Object> attributes) {
		return Arrays.stream(values())
                .filter(provider -> registrationId.equals(provider.registrationId))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new)
           .of.apply(attributes);
		
	}
	
}
