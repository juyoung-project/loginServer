package com.project.login.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.project.login.repository.Member;
import com.project.login.repository.MemberRepository;

@Service
public class UserDetailService implements UserDetailsService {

	@Autowired
	private MemberRepository repository;
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		// 실제로는 사용자 정보를 데이터베이스에서 조회해야 합니다.
		System.out.println("loadbyusername");
		Optional<Member> memOptional = repository.findByEmail(email);
        if (memOptional.isPresent()) {
        	Member member = memOptional.get();
        	// EMAIL을 KEY값으로 사용하기 위해 name대신 EMAIL 삽입
        	return User.withUsername(member.getEmail()).password(member.getPassword()).roles(member.getRole().name()).build();
        } else {
            throw new UsernameNotFoundException("User not found with username: " + email);
        }
	}
	
	public Member loadMemberInfo(String email) {
		Optional<Member> memOptional = repository.findByEmail(email);
        if (memOptional.isPresent()) {
        	Member member = memOptional.get();
        	return member;
        } else {
            throw new UsernameNotFoundException("User not found with username: " + email);
        }
	}

}
