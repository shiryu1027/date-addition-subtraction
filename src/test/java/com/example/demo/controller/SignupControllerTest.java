package com.example.demo.controller;

import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.example.demo.form.UserForm;
import com.example.demo.service.UserService;

@SpringBootTest
@AutoConfigureMockMvc
class SignupControllerTest {
	
	private MockitoSession session;

	@Autowired
	MockMvc mockMvc;
	
	@Mock
	UserService userService;
	
	@InjectMocks
	SignupController sut;
	
	@BeforeEach
	void setup() {
		this.mockMvc = MockMvcBuilders.standaloneSetup(sut).build();
		session = Mockito.mockitoSession().initMocks(this).startMocking();
	}
	
	@AfterEach
	void tearDown() throws Exception{
		session.finishMocking();
	}
	
	@Nested
	class サインアップ画面を表示する {
		
		@Test
		void _サインアップ画面を表示する() throws Exception{
			mockMvc.perform(get("/user/signup"))
				.andExpect(status().isOk())
				.andExpect(view().name("user/signup"));
		}
	}
	
	@Nested
	class ユーザーの新規登録が完了した後_サインイン画面にリダイレクトする {
		
		private UserForm userForm;
		
		@BeforeEach
		void setup() {
			userForm = new UserForm();
			userForm.setMailAddress("user@gmail.com");
			userForm.setPassword("Password64");
			userForm.setUsername("ユーザー");
		}
		
		@Test
		void サインイン画面にリダイレクトする() throws Exception{
			mockMvc.perform(post("/user/signup").flashAttr("userForm", userForm).with(csrf()))
				.andExpect(status().isFound())
				.andExpect(redirectedUrl("/user/signin"));
		}
		
		@Test
		void ユーザーを新規登録するメソッドが呼ばれる() throws Exception{
			doNothing().when(userService).signup(userForm);
			mockMvc.perform(post("/user/signup").flashAttr("userForm", userForm).with(csrf()))
				.andExpect(status().isFound());
			verify(userService,times(1)).signup(userForm);
		}
		
		@Test
		void 登録するユーザー情報にエラーがあれば_サインアップ画面を表示する() throws Exception{
			UserForm errorUserForm = new UserForm();
			errorUserForm.setMailAddress("user@gmail.com");
			errorUserForm.setPassword("");
			errorUserForm.setUsername("ユーザー");
			
			mockMvc.perform(post("/user/signup").flashAttr("userForm", errorUserForm).with(csrf()))
				.andExpect(status().isOk())
				.andExpect(view().name("user/signup"));
		}

	}
	
}
