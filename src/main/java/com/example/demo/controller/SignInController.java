package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.form.UserForm;

@Controller
@RequestMapping("/user")
public class SignInController {
	
	/* サインイン画面の表示 */
	@GetMapping("/signIn")
	public String signInDisplay() {
		return "user/signIn";
	}
	
	/*　サインイン、その後ホームページへリダイレクト */
	@PostMapping("/signIn")
	public String signIn(@ModelAttribute UserForm form) {
		return "redirect:/calc/";
	}
	
}
