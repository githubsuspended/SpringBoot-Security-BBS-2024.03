package com.example.security.service;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import com.example.security.entity.MyUserDetails;
import com.example.security.entity.SecurityUser;

@Service
public class MyOAuth2UserService extends DefaultOAuth2UserService {
	@Autowired SecurityUserService securityUserService;
	@Autowired private BCryptPasswordEncoder bCryptPasswordEncoder;
	private final Logger log = LoggerFactory.getLogger(getClass());
	
	// Provider(구글, github 등)로부터 받은 userRequest 데이터에 대해 후처리하는 메소드
	@Override
	public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
		String uid, email, uname, profile;
		String hashedPwd = bCryptPasswordEncoder.encode("Social Login");
		SecurityUser securityUser = null;
		log.info("getClientRegistration(): " + userRequest.getClientRegistration());	// 어떤 OAuth로 로그인 했는지 (예, google)
		
		OAuth2User oAuth2User = super.loadUser(userRequest);
		log.info("getAttributes(): " + oAuth2User.getAttributes());
		
		// 회원가입이 안 되었으면 회원가입 시켜줌
		String provider = userRequest.getClientRegistration().getRegistrationId();
		switch(provider) {
		case "google":
			String providerId = oAuth2User.getAttribute("sub");
			uid = provider + "_" + providerId;
			securityUser = securityUserService.findByUid(uid);
			if (securityUser == null) {				// 가입이 안되어있으면 가입 진행
				email = oAuth2User.getAttribute("email");
				uname = oAuth2User.getAttribute("name");
				profile = oAuth2User.getAttribute("picture");
				securityUser = new SecurityUser(uid, hashedPwd, uname, email, provider, profile);
				securityUserService.insertSecurityUser(securityUser);
				securityUser = securityUserService.findByUid(uid);
				log.info("구글 계정을 통해 회원가입이 되었습니다.");
			}
			break;
			
		case "github":
			int id = oAuth2User.getAttribute("id");
			uid = provider + "_" + id;
			securityUser = securityUserService.findByUid(uid);
			if (securityUser == null) {				// 가입이 안되어있으면 가입 진행
				email = oAuth2User.getAttribute("email");
				uname = oAuth2User.getAttribute("name");
				profile = oAuth2User.getAttribute("avatar_url");
				securityUser = new SecurityUser(uid, hashedPwd, uname, email, provider, profile);
				securityUserService.insertSecurityUser(securityUser);
				securityUser = securityUserService.findByUid(uid);
				log.info("깃허브 계정을 통해 회원가입이 되었습니다.");
			}
			break;
			
		case "naver":
			Map<String, Object> response = (Map) oAuth2User.getAttribute("response");
			String nid = (String) response.get("id");
			uid = provider + "_" + nid;
			securityUser = securityUserService.findByUid(uid);
			if (securityUser == null) {				// 가입이 안되어있으면 가입 진행
				email = (String) response.get("email");
				uname = (String) response.get("nickname");
				profile = (String) response.get("profile_image");
				profile = (profile == null) ? "/sbbs/file/download/profile/person.svg" : profile;
				securityUser = new SecurityUser(uid, hashedPwd, uname, email, provider, profile);
				securityUserService.insertSecurityUser(securityUser);
				securityUser = securityUserService.findByUid(uid);
				log.info("네이버 계정을 통해 회원가입이 되었습니다.");
			}
			break;
		
		case "kakao":
			long kid = (Long) oAuth2User.getAttribute("id");
			uid = provider + "_" + kid;
			securityUser = securityUserService.findByUid(uid);
			if (securityUser == null) {				// 가입이 안되어있으면 가입 진행
				Map<String, String> properties = (Map) oAuth2User.getAttribute("properties");
				Map<String, Object> account = (Map) oAuth2User.getAttribute("kakao_account");
				email = (String) account.get("email");
				uname = (String) properties.get("nickname");
				profile = (String) properties.get("profile_image");
				securityUser = new SecurityUser(uid, hashedPwd, uname, email, provider, profile);
				securityUserService.insertSecurityUser(securityUser);
				securityUser = securityUserService.findByUid(uid);
				log.info("카카오 계정을 통해 회원가입이 되었습니다.");
			}
			break;
		}
		return new MyUserDetails(securityUser, oAuth2User.getAttributes());
	}
	
}
