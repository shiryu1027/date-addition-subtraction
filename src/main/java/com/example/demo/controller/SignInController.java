package com.example.demo.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.WebAttributes;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
public class SignInController {  
	
	@Autowired
	HttpSession session;
	
	@Autowired
	HttpServletRequest request;
	
	/* サインイン画面の表示 */
	@GetMapping("/signin")
	public String signInDisplay(Model model) {
		
		model.addAttribute("notSignIn", "");
		
		/* 再度このMappingに来た時、エラーメッセージを消す処理 */
		if (!(request.getQueryString() == null)) { // nullはequalsで判定出来ない
			if (!(request.getQueryString().equals("error"))) { // URLの?以降の文字列表示
				session.setAttribute(WebAttributes.AUTHENTICATION_EXCEPTION, null); // SPRING_SECURITY_LAST_EXCEPTIONにnullをバインド
			} 
		} else { 
			session.setAttribute(WebAttributes.AUTHENTICATION_EXCEPTION, null);
		}
		
		return "user/signin";
	}
	
	/* Security.Configに指定したファイルに自動遷移(Controllerいらない) */
	
}

/* 課題点:更新ボタン(リロード)ではクエリ(?以降の文字列)に影響を与えることが出来ずに、エラーメッセージは残り続ける */
