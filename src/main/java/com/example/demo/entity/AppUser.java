package com.example.demo.entity;

import lombok.Data;

/* 単に"Userクラス"とすると、SpringセキュリティのUserクラスとかぶるため避ける */
@Data
public class AppUser {
	
	private int userId;
	
	private String mailAddress;
	
	private String username;
	
	private String password;
	
	private String role;
	
	public AppUser() {
		
	}
	
	public AppUser(int userId, String mailAddress, String username, String password, String role) {
		this.userId = userId;
		this.mailAddress = mailAddress;
		this.username = username;
		this.password = password;
		this.role = role;
	}
	
}
