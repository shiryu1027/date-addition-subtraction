package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.form.UserForm;
import com.example.demo.service.UsersService;

@Controller
@RequestMapping("/user")
public class SignupController {
	
	@Autowired
	UsersService service;
	
	/* サインアップ画面の表示 */
	@GetMapping("/signup")
	public String signupDisplay(@ModelAttribute UserForm form) {
		
		return "user/signup";
	}
	
	/* ユーザーの新規登録、その後サインイン画面にリダイレクト */
	@PostMapping("/signup")
	public String signUp(@Validated @ModelAttribute UserForm form, BindingResult result, Model model) {
		
		if (result.hasErrors()) {
			System.out.println(form);
			return signupDisplay(form);
		}
		
		service.signUp(form);
		
		return "redirect:/user/signin";
	}
	
}
