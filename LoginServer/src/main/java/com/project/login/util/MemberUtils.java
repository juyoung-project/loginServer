package com.project.login.util;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

public class MemberUtils {

	public static String getCurrentUserEmail() {
		
		String memberEmail = "";
		Object principal = MemberUtils.getCurrentPrincipal();
        if (principal instanceof UserDetails) {
        	memberEmail = ((UserDetails) principal).getUsername();
        	System.out.println("1 ::: > " + memberEmail);
        }
        return memberEmail;
    }
	
	private static Object getCurrentPrincipal() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	 
        if (authentication == null || !authentication.isAuthenticated()) {
            return null;
        }

        // 인증된 사용자의 이름 반환
        return authentication.getPrincipal(); 
	}
}
 