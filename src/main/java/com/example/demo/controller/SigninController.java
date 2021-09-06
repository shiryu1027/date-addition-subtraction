package com.example.demo.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.WebAttributes;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
public class SigninController {  
	
	@Autowired
	HttpSession session;
	
	@Autowired
	HttpServletRequest request;
	
	/* サインイン画面の表示 */
	@GetMapping("/signin")
	public String signinDisplay() {
		
		/* エラーメッセージを消す処理(更新ボタンを除く) */
		if (request.getQueryString() == null || request.getQueryString().equals("")) {
			session.setAttribute(WebAttributes.AUTHENTICATION_EXCEPTION, null); // SPRING_SECURITY_LAST_EXCEPTIONにnullをバインド
		} 
		
		return "user/signin";
	}
	
}

/* 
 * TODO:更新ボタン(リロード)ではクエリ(?以降の文字列)に影響を与えることが出来ずに、エラーメッセージは残り続ける 
 */