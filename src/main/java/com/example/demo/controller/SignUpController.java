package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.form.UserForm;
import com.example.demo.service.UsersService;

@Controller
@RequestMapping("/user")
public class SignUpController {
	
	@Autowired
	UsersService service;
	
	/* サインアップ画面の表示 */
	@GetMapping("/signUp")
	public String signUpDisplay(Model model) {
		
		model.addAttribute("notSignIn", "");
		
		return "user/signUp";
	}
	
	/* ユーザーの新規登録、その後サインイン画面にリダイレクト */
	@PostMapping("/signUp")
	public String signUp(@ModelAttribute UserForm form) {
		service.signUp(form);
		return "redirect:/user/signIn";
	}
	
}
