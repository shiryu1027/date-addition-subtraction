package com.example.demo.form;

import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;

import lombok.Data;

@Data
public class UserForm {
	
	private int userId;
	
	@Length(min=1, max=50, message="50文字以内のメールアドレスを入力して下さい")
	//@NotEmpty(message="メールアドレス必須項目です") // lengthと被っているためいらないかも
	private String mailAddress;
	
	@Length(min=1, max=10, message="10文字以内のユーザーネームを入力して下さい")
	//@NotEmpty(message="ユーザーは必須項目です")
	private String username;
	
	@Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])[a-zA-Z0-9]{10,25}$",
			message = "パスワードは大小英文字(a-z、A-Z)、数字(0-9)の3種を必ず一回は使用して下さい")
	//@NotEmpty(message="パスワードは必須項目です")
	private String password;
	
	private String role;
	
}