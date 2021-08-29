package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
public class SignInController {
	
	/* サインイン画面の表示 */
	@GetMapping("/signIn")
	public String signInDisplay(Model model) {
		
		model.addAttribute("notSignIn", "");
		
		return "user/signIn";
	}
	
	/* Security.Configに指定したファイルに自動遷移(Controllerいらない) */
	
}
