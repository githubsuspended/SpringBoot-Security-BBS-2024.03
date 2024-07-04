package com.example.security.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.security.entity.MyUserDetails;
import com.example.security.entity.SecurityUser;

// Security 설정에서 loginProcessingUrl("/security-user/login") 설정되어 있으므로
// 로그인 요청이 오면 자동으로 UserDetailsService 타입으로 IoC되어 있는
// loadUserByUsername() method가 실행됨
@Service
public class MyUserDetailsService implements UserDetailsService {
	@Autowired private SecurityUserService securityService;
	private final Logger log = LoggerFactory.getLogger(getClass());

	// Security Session 내에 (Authentication 내에 UserDetails)이 들어감
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		SecurityUser securityUser = securityService.findByUid(username);
		
		if (securityUser != null) {
			log.info("Login 완료: " + securityUser.getUid());
			return new MyUserDetails(securityUser);
		}
		return null;
	}

}
