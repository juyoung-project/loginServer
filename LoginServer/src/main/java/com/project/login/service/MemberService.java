package com.project.login.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.project.login.common.Role;
import com.project.login.repository.Member;
import com.project.login.repository.MemberRepository;
import com.project.login.util.MemberUtils;
import com.project.login.util.StringUtils;

@Service
public class MemberService {
	
	@Autowired
	private MemberRepository memberRepository;
	
	@Autowired
	private RedisTemplate<String, String> redisTemplate;
	
	public void signUp(Map<String, Object> data) {
		
		Member mem = Member.builder()
				.email( StringUtils.ConverterObj(  data.get("email") ) )
				.name( StringUtils.ConverterObj(  data.get("name") ) )
				.password( StringUtils.ConverterObj( data.get("password") ))
				.role(Role.NORMAL)
				.build();
		
		memberRepository.save(mem);
		
	}
	
	public Long getLoginUserCount() {
		return redisTemplate.opsForSet().size("JWT-TOKEN:"+MemberUtils.getCurrentUserEmail());
	}
	
}
