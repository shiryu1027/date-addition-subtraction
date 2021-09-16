package com.example.demo.entity;

import lombok.Data;

/* 単に"Userクラス"とすると、SpringセキュリティのUserクラスとかぶるため避ける */
@Data
public class AppUser {
	
	private int userId;
	
	private String mailAddress;
	
	private String nickname;
	
	private String password;
	
	private String role;
	
	public AppUser() {
		
	}
	
	public AppUser(int userId, String mailAddress, String nickname, String password, String role) {
		this.userId = userId;
		this.mailAddress = mailAddress;
		this.nickname = nickname;
		this.password = password;
		this.role = role;
	}
	
}
