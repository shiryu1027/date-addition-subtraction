package com.example.demo.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
class SigninControllerTest {
	
	@Autowired
	MockMvc mockMvc;
	
	@Nested
	class サインイン画面を表示する {
		
		@Test
		void _サインイン画面を表示する() throws Exception {
			mockMvc.perform(get("/user/signin"))
				.andExpect(status().isOk())
				.andExpect(view().name("user/signin"));
		}
	}
	
}
