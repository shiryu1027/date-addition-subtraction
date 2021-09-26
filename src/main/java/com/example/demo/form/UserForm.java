package com.example.demo.form;

import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;

import lombok.Data;

@Data
public class UserForm {
	
	private int userId;
	
	@Length(min=1, max=50, message="50文字以内のメールアドレスを入力して下さい")
	@NotEmpty(message="メールアドレス必須項目です")
	private String mailAddress;
	
	@Length(min=1, max=40, message="40文字以内のニックネームを入力して下さい")
	@NotEmpty(message="ユーザーは必須項目です")
	private String username;
	
	@NotEmpty(message="パスワードは必須項目です")
	private String password;
	
	private String role;
	
}