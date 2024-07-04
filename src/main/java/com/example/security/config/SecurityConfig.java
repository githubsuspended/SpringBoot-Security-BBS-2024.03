package com.example.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.example.security.service.MyOAuth2UserService;

import jakarta.servlet.DispatcherType;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	@Autowired private MyOAuth2UserService myOAuth2UserService;

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.csrf(auth -> auth.disable())
			.headers(config -> config			// CK Editor image upload
				.frameOptions(optionConfig -> optionConfig.disable())
			)
			.authorizeHttpRequests(auth -> auth			// SpringSecurity version6 부터 람다식 사용
				.dispatcherTypeMatchers(DispatcherType.FORWARD).permitAll()
				.requestMatchers("/user/register", "/user/login", "/oauth2/**",
						"/img/**", "/css/**", "/js/**", "/error/**").permitAll()
				.requestMatchers("/admin/**").hasAuthority("ADMIN")
				.anyRequest().authenticated()
			)
			.formLogin(auth -> auth
				.loginPage("/user/login")				// 로그인 폼
				.loginProcessingUrl("/user/login")		// Spring Security가 낚아챔, UserDetailsService 구현객체에서 처리해주어야 함
				.usernameParameter("uid")
				.passwordParameter("pwd")
				.defaultSuccessUrl("/user/loginSuccess", true)
				.permitAll()
			)
			.oauth2Login(auth -> auth
				.loginPage("/user/login")
				// 소셜 로그인이 완료된 이후의 후처리
				// 1. 코드받기(인증), 2. 액세스 토큰(권한), 3. 사용자 프로필 정보를 가져옴
				// 4-1. 그 정보를 토대로 회원가입을 자동으로 진행시키기도 함
				// 4-2. 프로바이더가 제공하는 정보 + 추가적인 정보를 수집함(예, 주소, 등급 등)
				.userInfoEndpoint(user -> user.userService(myOAuth2UserService))
				.defaultSuccessUrl("/user/loginSuccess", true)
			)
			.logout(auth -> auth
				.logoutUrl("/user/logout")
				.invalidateHttpSession(true)		// 로그아웃시 세션 초기화
				.deleteCookies("JSESSIONID")		// 로그아웃시 쿠키 삭제
				.logoutSuccessUrl("/user/login")
			);
		
		return http.build();
	}
	
}
