package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.entity.AppUser;
import com.example.demo.form.UserForm;
import com.example.demo.mapper.UsersMapper;

@Service
public class UserService {
	
	@Autowired
	UsersMapper mapper;
	
	@Autowired
	PasswordEncoder encoder;
	
	/* ユーザー新規登録(insert) */
	public void signup(UserForm user) {
		
		/* パスワード暗号化 */
		String rawPassword = user.getPassword(); // 生のpassword取得
		user.setPassword(encoder.encode(rawPassword)); // 暗号化
		user.setRole("ROLE_GENERAL"); // 権限追加
		mapper.signup(user);
	}
	
	/* サインイン情報取得 */
	public AppUser getSigninUser(String mailAddress) {
		return mapper.findOne(mailAddress);
	}
	
	/* ニックネームの取得 */
	public String getUsername(String mailAddress) {
		return mapper.getUsername(mailAddress);
	}
	
	/* ユーザー情報更新(パスワードを除く) */
	public void alterUser(UserForm user) {
		mapper.updateUser(user);
	}
	
}
