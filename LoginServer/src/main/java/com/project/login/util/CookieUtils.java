package com.project.login.util;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CookieUtils {
	
	private static final int EXPIRATION = 600;
	private static final int REFRESH_EXPIRATION = 7 * 24 * 60 * 60 ;
	private static final String JWT_COOKIE = "JWT-TOKEN";
	private static final String JWT_REFRESH_COOKIE = "RE-JWT-TOKEN";
	
	
	private CookieUtils() {}
	
	public static void addJwtTokenCookie(HttpServletResponse response, String token) {
		_addCookie(response, token, JWT_COOKIE, EXPIRATION);
	}
	
	public static void addJwtRefreshTokenCookie(HttpServletResponse response, String token) {
		_addCookie(response, token, JWT_REFRESH_COOKIE, REFRESH_EXPIRATION);
	}
	
	private static void _addCookie(HttpServletResponse response, String token, String cookieType, int expiration) {
		Cookie cookie = new Cookie(cookieType, token);
		cookie.setMaxAge(expiration);
	    cookie.setHttpOnly(true);
	    cookie.setSecure(true); 
	    cookie.setPath("/");      
	    response.addCookie(cookie);
	}
	
	public static void clearJwtToken(HttpServletResponse response) {
		Cookie cookie = new Cookie(JWT_COOKIE, null);
		cookie.setMaxAge(0);
		cookie.setPath("/");
		response.addCookie(cookie);
	}
	
	public static String getJwtFromRequest(HttpServletRequest request, String cookieType) {
		String jwtToken = "";
		Cookie[] cookies = request.getCookies();
	    if (cookies != null) {
	        for (Cookie cookie : cookies) {
	        	if (cookieType.equals(cookie.getName())) {
	        		jwtToken = cookie.getValue();
	        		break;
	        	}
        	}
	    }
	    return jwtToken;
	 }

	
}
