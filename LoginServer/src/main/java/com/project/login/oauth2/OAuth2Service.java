package com.project.login.oauth2;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import com.project.login.common.Role;
import com.project.login.repository.Member;
import com.project.login.repository.MemberRepository;

@Service
public class OAuth2Service implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

	@Autowired
	private MemberRepository memberRepository;
	
	@Override
	public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
		
		OAuth2UserService<OAuth2UserRequest, OAuth2User> service = new DefaultOAuth2UserService();
		OAuth2User oAuth2User = service.loadUser(userRequest); // OAuth2 정보를 가져옵니다.

		Map<String, Object> originAttributes = oAuth2User.getAttributes(); // OAuth2User의 attribute
		System.out.println("1번 먼저 작동 :: > "+originAttributes);
		System.out.println(originAttributes.get("response"));
//		String userNameAttributeName = userRequest.getClientRegistration().getProviderDetails()
//                .getUserInfoEndpoint().getUserNameAttributeName(); // OAuth 로그인 시 키(pk)가 되는 값 
		
		String registrationId = userRequest.getClientRegistration().getRegistrationId(); // 로그인 원천지 (google, kakao, naver)
		Member member = Oauth2Provider.getOauthInfo(registrationId, originAttributes);
		this.saveOrUpdate(member);
		
		return new DefaultOAuth2User(
                Collections.singleton(new SimpleGrantedAuthority("NORMAL") ),
                (Map<String, Object>) originAttributes.get("response"),
				"email");
		
	}
	
	private void saveOrUpdate(Member memberEntity) {
		Optional<Member> memberOptional = memberRepository.findByEmail(memberEntity.getEmail());
		Member member = null;
		
		if ( memberOptional.isPresent() ) {
			// 데이터가 있을경우
		} else {
			member = Member.builder()
					.email(memberEntity.getEmail())
					.name(memberEntity.getName())
					.password(UUID.randomUUID().toString())
					.role(Role.NORMAL)
					.build();
			memberRepository.save(member);
		}
		
	}

}
