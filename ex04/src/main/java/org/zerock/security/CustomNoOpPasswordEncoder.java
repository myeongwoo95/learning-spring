package org.zerock.security;

import org.springframework.security.crypto.password.PasswordEncoder;

import lombok.extern.log4j.Log4j;

//사용안함, 실제로 인코딩은 안하는 클래스임 테스트용
@Log4j
public class CustomNoOpPasswordEncoder implements PasswordEncoder{
	
	public String encode(CharSequence rawPassword) {
		
		log.warn("before encode: " + rawPassword);
		
		return rawPassword.toString();
	}
	
	public boolean matches(CharSequence rawPassword, String encodePassword) {
		
		log.warn("matches: " + rawPassword + ":" + encodePassword);
		
		return rawPassword.toString().equals(encodePassword);
	}
}
