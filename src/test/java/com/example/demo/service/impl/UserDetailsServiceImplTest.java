package com.example.demo.service.impl;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.form.UserForm;
import com.example.demo.service.UserService;

@SpringBootTest
@Transactional
@TestPropertySource(locations = "classpath:test.properties")
@DisplayName("UserDetailsServiceImplの結合テスト")
class UserDetailsServiceImplTest {
	
	private String mailAddress = "user@gmail.co.jp";
	private String rawPassword = "Password84";
	private String username = "ユーザー";
	
	@Autowired
	private UserDetailsServiceImpl sut;
	
	@Autowired
    private UserService userService;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@BeforeEach
	void setup() {
		// データベースのセットアップ
		// 前提条件:signupメソッドのテストが完了している
		UserForm user = new UserForm();
		user.setMailAddress(mailAddress);
		user.setPassword(rawPassword);
		user.setUsername(username);
		userService.signup(user);
	}
	
	@Test
	void ユーザ名が存在するときユーザ詳細を取得する() throws Exception{
		UserDetails actual = sut.loadUserByUsername(mailAddress);
		
		assertThat(actual.getUsername()).isEqualTo(mailAddress); 
		// actual.getUsername()はメールアドレスを取得		
		assertThat(passwordEncoder.matches(rawPassword, actual.getPassword())).isTrue();
	}
	
	@Test
	void ユーザ名が存在しないとき例外をスローする() {
		assertThatThrownBy(
				() -> {sut.loadUserByUsername("user@yahoo.co.jp");}
		).isInstanceOf(
				UsernameNotFoundException.class
		).hasMessageContaining("user not found");
		
	}
}
