package com.example.demo.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.dao.DataAccessException;

import com.example.demo.entity.AppUser;
import com.example.demo.form.UserForm;

@Mapper
public interface UsersMapper {
	
	/* ユーザー新規登録 */
	void signup(UserForm user) throws DataAccessException;
	
	/* サインインユーザーの取得(UserDetailsServiceImpl内からの呼び出し) */
	AppUser findOne(String mailAddress);
	
	/* ニックネームの取得 */
	String getUsername(String mailAddress);
	
	/* ユーザー情報更新(パスワードを除く) */
	void updateUser(UserForm user);
	
}
