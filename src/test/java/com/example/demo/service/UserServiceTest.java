package com.example.demo.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoSession;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.demo.entity.AppUser;
import com.example.demo.form.UserForm;
import com.example.demo.mapper.UsersMapper;

@SpringBootTest
class UserServiceTest {
	
	private MockitoSession session;
	
	@Mock
	UsersMapper mapper;
	
	@Mock
	PasswordEncoder encoder;
	
	@InjectMocks
	UserService target;
	
	@BeforeEach
	void setup() {
		session = Mockito.mockitoSession().initMocks(this).startMocking();
	}
	
	@AfterEach
	void tearDown() {
	    session.finishMocking();
	}
	
	@Nested
	class ユーザーの新規登録 {
		
		private UserForm userForm;
		
		@BeforeEach
		void setup() {
			userForm = new UserForm();
			userForm.setUserId(1);
			userForm.setMailAddress("user@co.jp");
			userForm.setUsername("ユーザー");
			userForm.setPassword("password");
			doNothing().when(mapper).signup(userForm);
			doReturn("encryptionPassword").when(encoder).encode("password");
			target.signup(userForm);
		}
		
		@Test
		void パスワードを暗号化するメソッドが呼ばれている() throws Exception {
			verify(encoder,times(1)).encode("password");
		}
		
		@Test
		void roleにROLE_GENERALが追加されている() throws Exception {
			assertThat(userForm.getRole()).isEqualTo("ROLE_GENERAL");
		}
		
		@Test
		void UserMapperのsignUpメソッドが一回呼ばれる() throws Exception {
			verify(mapper,times(1)).signup(userForm);
		}
	}
	
	@Nested
	class サインイン情報の取得 {
		
		private String mailAddress;
		private AppUser appUser;
		private AppUser actual;
		
		@BeforeEach
		void setup() {
			mailAddress = "user@co.jp";
			appUser = new AppUser();
			appUser.setUserId(1);
			appUser.setMailAddress("user@co.jp");
			appUser.setUsername("ユーザー");
			appUser.setPassword("password");
			doReturn(appUser).when(mapper).findOne(mailAddress);
			actual = target.getSigninUser(mailAddress);
		}
		
		@Test
		void UserMapperのfindOneメソッドが一回呼ばれる() throws Exception {
			
			verify(mapper,times(1)).findOne(mailAddress);
		}
		
		@Test
		void メールアドレスからAppUser型のユーザーデータを返す() throws Exception {
			assertThat(actual.getUserId()).isEqualTo(1);
		}
	}
	
	@Nested
	class ニックネームの取得 {
		
		private String mailAddress;
		private String nickname;
		String actual;
		
		@BeforeEach
		void setup() {
			mailAddress = "user@co.jp";
			nickname = "ユーザー";
			doReturn(nickname).when(mapper).getUsername(mailAddress);
			actual = target.getUsername(mailAddress);
		}
		
		@Test
		void UserMapperのgetUserNicknameメソッドが一回呼ばれる() throws Exception {
			
			verify(mapper,times(1)).getUsername(mailAddress);
		}
		
		@Test
		void mailAddressからニックネームが取得できる() throws Exception {
			assertThat(actual).isEqualTo(nickname);
		}
	}
	
	@Nested
	class ユーザー情報更新 {
		
		private UserForm userForm;
		
		@BeforeEach
		void setup() {
			userForm = new UserForm();
			userForm.setUserId(1);
			userForm.setMailAddress("user@co.jp");
			userForm.setUsername("ユーザー");
			userForm.setPassword("password");
			userForm.setRole("ROLE_GENERAL");
			doNothing().when(mapper).updateUser(userForm);
			target.alterUser(userForm);
		}
		
		@Test
		void UserMapperのupdateUserメソッドが一回呼ばれる() throws Exception {
			verify(mapper,times(1)).updateUser(userForm);
		}
	}
}
