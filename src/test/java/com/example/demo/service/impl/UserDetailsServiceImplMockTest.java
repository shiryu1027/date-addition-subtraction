package com.example.demo.service.impl;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.demo.entity.AppUser;
import com.example.demo.service.UserService;

@SpringBootTest
class UserDetailsServiceImplMockTest {
	
	private MockitoSession session;
	private AppUser signinUser;
	private String mailAddress;
	private String password;
	
	@Mock
	UserService userService;
	
	@InjectMocks
	UserDetailsServiceImpl target;
	
	@Autowired
	PasswordEncoder passwordEncoder;
	
	@BeforeEach
	void setup() {
		session = Mockito.mockitoSession().initMocks(this).startMocking();
		mailAddress = "user@gmail.co.jp";
		password = passwordEncoder.encode("Password84");
		signinUser = new AppUser();
		signinUser.setUserId(1);
		signinUser.setMailAddress(mailAddress);
		signinUser.setUsername("ユーザー");
		signinUser.setPassword(password);
		signinUser.setRole("ROLE_GENERAL");
	}
	
	@AfterEach
	void tearDown() {
	    session.finishMocking();
	}
	
	@Test
	void ユーザ名が存在するときユーザ詳細を取得する() {
		// 準備
		doReturn(signinUser).when(userService).getSigninUser(mailAddress);
		
		// 実行
		UserDetails actual = target.loadUserByUsername(mailAddress);
		
		// 検証
		assertThat(actual.getUsername()).isEqualTo(signinUser.getMailAddress());
		assertThat(actual.getPassword()).isEqualTo(signinUser.getPassword());
	}
	
	@Test
	void ユーザ名が存在しないとき例外をスローする() {
		doReturn(null).when(userService).getSigninUser("user@yahoo.co.jp");
		// モックを使うと、このテストはUserDetailsServiceImplTestのみのテスト(他クラスに依存しない)になってしまう
		
		assertThatThrownBy(
				() -> {target.loadUserByUsername("user@yahoo.co.jp");}
		).isInstanceOf(
				UsernameNotFoundException.class
		).hasMessageContaining("user not found");
		
	}

}
