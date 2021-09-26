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
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.example.demo.entity.AppUser;
import com.example.demo.service.UsersService;

@SpringBootTest
class UserDetailsServiceImplTest {
	
	private MockitoSession session;
	private AppUser signInUser;
	private String username;
	
	@Mock
	UsersService usersService;
	
	@InjectMocks
	UserDetailsServiceImpl target;
	
	@BeforeEach
	void setup() {
		session = Mockito.mockitoSession().initMocks(this).startMocking();
		username="user@co.jp";
	}
	
	@AfterEach
	void tearDown() {
	    session.finishMocking();
	}
	
	@Test
	void ユーザ名が存在するときユーザ詳細を取得する() {
		// 準備
		signInUser = new AppUser();
		signInUser.setUserId(1);
		signInUser.setMailAddress(username);
		signInUser.setUsername("ユーザー");
		signInUser.setPassword("password");
		signInUser.setRole("ROLE_GENERAL");
		doReturn(signInUser).when(usersService).getSignInUser(username);
		
		// 実行
		UserDetails actual = target.loadUserByUsername(username);
		
		// 検証
		assertThat(actual.getUsername()).isEqualTo(signInUser.getMailAddress());
	}
	
	@Test
	void ユーザ名が存在しないとき例外をスローする() {
		
		doReturn(signInUser).when(usersService).getSignInUser(username);
		
		assertThatThrownBy(
				() -> {target.loadUserByUsername(username);}
		).isInstanceOf(
				UsernameNotFoundException.class
		).hasMessageContaining("user not found");
		
		// JUnitのアサーション
		/*assertThrows(UsernameNotFoundException.class,
				() -> target.loadUserByUsername(username));*/
	}

}
