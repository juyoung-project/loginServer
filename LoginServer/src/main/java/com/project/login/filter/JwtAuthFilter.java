package com.project.login.filter;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.project.login.common.Code;
import com.project.login.common.PermitAllUrl;
import com.project.login.common.Role;
import com.project.login.exception.LoginException;
import com.project.login.jwt.JwtTokenProvider;
import com.project.login.service.UserDetailService;
import com.project.login.util.CookieUtils;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {
	
	private final String TOKEN_PREFIX = "JWT-TOKEN:";
	private final String REFRESH_TOKEN_PREFIX = "RE-JWT-TOKEN:";
	
	private static final String JWT_COOKIE = "JWT-TOKEN";
	private static final String JWT_REFRESH_COOKIE = "RE-JWT-TOKEN";
	
	@Autowired
	private JwtTokenProvider jwtTokenProvider;
	
	@Autowired
	private UserDetailService userDetailService;
	 
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
	        throws ServletException, IOException {

	    String requestURI = request.getRequestURI();
	    String token = CookieUtils.getJwtFromRequest(request, JWT_COOKIE);
	    
	    if (isPermittedURL(requestURI)) {
	        filterChain.doFilter(request, response);
	        return;
	    }

	    if (isValidToken(token, TOKEN_PREFIX)) {
	        authenticateUser(token, request);
	    } else {
	        token = CookieUtils.getJwtFromRequest(request, JWT_REFRESH_COOKIE);
	        System.out.println("restone:: > " + token);
	        if (isValidToken(token, REFRESH_TOKEN_PREFIX)) {
	            authenticateUser(token, request);
	            setCookieInToken(response, token);
	        } else {
	            throw new LoginException(Code.TOKEN_EXPIRED, requestURI + " 토큰이 만료되었거나 올바르지 않는 토큰입니다.");
	        }
	    }
	    
	    filterChain.doFilter(request, response);
	}

	private boolean isPermittedURL(String requestURI) {
	    return Arrays.stream(PermitAllUrl.PermitURL.getPermitURL())
	                 .anyMatch(str -> str.contains(requestURI));
	}

	private boolean isValidToken(String token, String tokenType) {
	    return token != null && jwtTokenProvider.validateToken(token, tokenType);
	}

	private void authenticateUser(String token, HttpServletRequest request) {
	    String email = jwtTokenProvider.getEmailFromToken(token);
	    UserDetails userDetails = userDetailService.loadUserByUsername(email);
	    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
	            userDetails, null, userDetails.getAuthorities());
	    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
	    SecurityContextHolder.getContext().setAuthentication(authentication);
	}

	private void setCookieInToken(HttpServletResponse response, String token) {
	    String email = jwtTokenProvider.getEmailFromToken(token);
	    CookieUtils.addJwtRefreshTokenCookie(response, jwtTokenProvider.generateRefreshToken(email, Role.NORMAL));
	    CookieUtils.addJwtTokenCookie(response, jwtTokenProvider.generateToken(email, Role.NORMAL));
	}
	
	
}
