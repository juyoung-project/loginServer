package com.project.login.oauth2;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.project.login.common.Role;
import com.project.login.jwt.JwtTokenProvider;
import com.project.login.util.CookieUtils;
import com.project.login.util.StringUtils;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class OauthSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
	
	@Autowired
	private JwtTokenProvider jwtTokenProvider;
	
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		
		System.out.println("2번작동");
		OAuth2User oAuth2User = (OAuth2User)authentication.getPrincipal();
		String email = StringUtils.ConverterObj( oAuth2User.getAttributes().get("email") );
		
		String jwtToken = jwtTokenProvider.generateToken(email, Role.NORMAL);
		String refreshToken = jwtTokenProvider.generateRefreshToken(email, Role.NORMAL);
		
		CookieUtils.addJwtTokenCookie(response, jwtToken);
		CookieUtils.addJwtRefreshTokenCookie(response, refreshToken);

		
		response.sendRedirect("/main");
	}

}
