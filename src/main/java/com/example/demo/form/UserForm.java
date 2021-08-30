package com.example.demo.form;

import lombok.Data;

@Data
public class UserForm {
	
	private int userId;
	
	private String mailAddress;
	
	private String nickname;
	
	private String password;
	
	private String role;
	
}