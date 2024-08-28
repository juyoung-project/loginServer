package com.project.login.controller;


import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.login.common.Code;
import com.project.login.common.ResponsePojo;
import com.project.login.common.Role;
import com.project.login.exception.LoginException;
import com.project.login.jwt.JwtTokenProvider;
import com.project.login.repository.Member;
import com.project.login.service.MemberService;
import com.project.login.service.UserDetailService;
import com.project.login.util.CookieUtils;
import com.project.login.util.MemberUtils;
import com.project.login.util.StringUtils;

@RestController
public class InitController {

	@Autowired
	private MemberService memService;
	
	@Autowired
	private JwtTokenProvider jwtTokenProvider;
	
	@Autowired
	private AuthenticationManager authenticationManager; 
	
	@Autowired
	private UserDetailService userDetailService;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@GetMapping(value = "/health/check")
	public ResponsePojo healthCheck() {
		System.out.println("sayHellow1111");
		
		return ResponsePojo.success(null, "sayhellow");
	}
	
	@RequestMapping(value = "/sign-up")
	public ResponsePojo signUp(@RequestBody Map<String, Object> reqData) {
		
		System.out.println(reqData);
		
		String password = StringUtils.ConverterObj( reqData.get("password") );
		reqData.put("password", passwordEncoder.encode(password));
		memService.signUp(reqData);
		return ResponsePojo.success(null, "sayhellow");
	}
	
	@RequestMapping(value = "/sign-in")
	public ResponsePojo signIn(@RequestBody Map<String, Object> reqData, HttpServletResponse response) throws Exception {
		  // 1. 사용자의 자격 증명을 포함한 Authentication 객체 생성
        UsernamePasswordAuthenticationToken authenticationToken =  new UsernamePasswordAuthenticationToken(reqData.get("email"), reqData.get("password"));
        // 2. AuthenticationManager를 통해 인증 시도
        try {
        	 authenticationManager.authenticate(authenticationToken);
        	 System.out.println("중간에먼저 확인하기");
             Member mem = userDetailService.loadMemberInfo(StringUtils.ConverterObj( reqData.get("email")) ) ;
             // 4. JWT 토큰 생성
             String jwtToken = jwtTokenProvider.generateToken( mem.getEmail(), mem.getRole());
         	 String refreshToken = jwtTokenProvider.generateRefreshToken(mem.getEmail(), mem.getRole());
    		
         	 CookieUtils.addJwtRefreshTokenCookie(response, refreshToken);
             CookieUtils.addJwtTokenCookie(response, jwtToken);
        } catch (Exception e) {
        	e.printStackTrace();
        	throw new LoginException(Code.ERROR, "정보가 올바르지 않습니다.");
		}
		return ResponsePojo.success(null, "전달");
	}
	
	@RequestMapping(value = "/api/test")
	public ResponsePojo test(@RequestBody Map<String, Object> reqData) {
		System.out.println("token 확인 :::>>>");
		System.out.println(MemberUtils.getCurrentUserEmail());
		return ResponsePojo.success(null, "testr");
	}
	
	@RequestMapping(value = "/api/test2")
	public ResponsePojo test2(@RequestBody Map<String, Object> reqData) {
		System.out.println("token2 확인 :::>>>");
		System.out.println(MemberUtils.getCurrentUserEmail());
		return ResponsePojo.success(null, "testr");
	}
	
	@RequestMapping(value = "/api/member-count")
	public ResponsePojo memberCount(@RequestBody Map<String, Object> reqData) {
		Map<String, Object> resMap = new HashMap<>();
		resMap.put("data", memService.getLoginUserCount());
		return ResponsePojo.success(resMap, "testr");
	}
}
