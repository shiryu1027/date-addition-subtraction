package com.example.demo.mapper;

import static org.assertj.core.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.entity.AppUser;
import com.example.demo.form.UserForm;

@SpringBootTest
@MybatisTest
@Transactional
@TestPropertySource(locations = "classpath:test.properties")
class UsersMapperTest {

	@Autowired
	UsersMapper usersMapper;
	
	@Autowired
	private NamedParameterJdbcOperations namedParameterJdbcOperations;
	
	@Autowired
	private JdbcOperations jdbcOperations; 
	
	@Test
	void ユーザー情報をDBに新規登録() throws Exception{
		UserForm userForm = new UserForm();
		userForm.setMailAddress("user3@gmail.com");
		userForm.setUsername("ユーザー3");
		userForm.setPassword("password");
		userForm.setRole("ROLE_GENERAL");
		
		usersMapper.signup(userForm);
		
		List<AppUser> users = 
				jdbcOperations.query("SELECT * FROM users",
						new BeanPropertyRowMapper<>(AppUser.class));
		
		assertThat(users.size()).isEqualTo(3);
		assertThat(users.get(2).getMailAddress()).isEqualTo("user3@gmail.com");
	}

	@Test
	void 入力されたメールアドレスから_ユーザー情報の一件取得() throws Exception{
		AppUser actualUser= usersMapper.findOne("user@gmail.com");
		
		assertThat(actualUser.getUserId()).isEqualTo(1);
		assertThat(actualUser.getUsername()).isEqualTo("ユーザー");
	}
	
	
	@Test
	void メールアドレスから_ユーザーネームを取得する() throws Exception{
		String username = usersMapper.getUsername("user@gmail.com");
		
		assertThat(username).isEqualTo("ユーザー");
	}
	
	@Test
	void ユーザー情報を更新する() throws Exception{
		UserForm userForm = new UserForm();
		userForm.setUserId(1);
		userForm.setMailAddress("kishida@gmail.com");
		userForm.setUsername("ユーザー");
		userForm.setPassword("password");
		userForm.setRole("ROLE_GENERAL");
		
		usersMapper.updateUser(userForm);
		
		List<AppUser> actualUsers = 
				namedParameterJdbcOperations.query("SELECT * FROM users",
						new BeanPropertyRowMapper<>(AppUser.class));
		
		assertThat(actualUsers.get(0).getMailAddress()).isEqualTo("kishida@gmail.com");
		assertThat(actualUsers.get(1).getMailAddress()).isNotEqualTo("kishida@gmail.com");
	}
}
