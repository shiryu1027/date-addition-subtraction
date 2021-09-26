package com.example.demo.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.entity.AppUser;
import com.example.demo.form.UserForm;
import com.example.demo.service.UserService;

@Controller
@RequestMapping("/user")
public class CalcUserController {
	
	@Autowired
	UserService userService;
	
	@Autowired
	PasswordEncoder encoder;

	@GetMapping("/update-id-name")
	public String usersUpdateDisplay(Principal principal, Model model) {
		
		String name = principal.getName();
		
		AppUser userData = userService.getSignInUser(name);
		
		model.addAttribute("userData", userData);
		model.addAttribute("nickname", userService.getSignInUser(name).getUsername());
		
		return "user/update-id-name";
	}
	
	/* バリデーション効かない→これ専用のFormを作るべき?? */
	@PostMapping("/update-id-name")
	public String usersUpdate(@RequestParam String mailAddress, @RequestParam String nickname, Principal principal, Model model) {
		
		String name = principal.getName();
		
		/*if(result.hasErrors()) {
			
			System.out.println(form);
			System.out.println(result);
			
			return usersUpdateDisplay(principal, model);
		}*/
		
		AppUser user = userService.getSignInUser(name);
		
		UserForm form = new UserForm();
		
		form.setUserId(user.getUserId());
		form.setPassword(user.getPassword());
		form.setMailAddress(mailAddress);
		form.setUsername(nickname);
		
		System.out.println(form);
		userService.updateUser(form);
		
		return "redirect:/calc/";
	}
	
	@GetMapping("/update-password")
	public String usersUpdatePasswordDisplay(Principal principal, Model model) {
		
		String name = principal.getName();
		
		model.addAttribute("nickname", userService.getSignInUser(name).getUsername());
		
		return "user/update-password";
	}
	
	/* 課題点:
	 *  (セキュリティ問題は度外視)
	 *  エンコードは全く一緒じゃないので、一致させられない。
	 *  securityconfigやuserdetailsserviceimplが使えないか調べる
	 *  */
	@PostMapping("/update-password")
	public String usersUpdatePassword(@RequestParam String password1, @RequestParam String password2, @RequestParam String password3, Principal principal, Model model) {
		
		String name = principal.getName();
		
		AppUser user = userService.getSignInUser(name);
		
		UserForm form = new UserForm();
		
		form.setUserId(user.getUserId());
		form.setMailAddress(user.getMailAddress());
		form.setUsername(user.getUsername());
		
		System.out.println(user.getPassword());
		System.out.println(encoder.encode(password1));
		System.out.println(encoder.matches(password1, user.getPassword()));
		System.out.println(encoder.matches(password2, encoder.encode(password3)));
		
		if(encoder.matches(password2, encoder.encode(password3))) {
			
			if (password2.equals(password3)) {
				
				form.setPassword(encoder.encode(password2));
				userService.updateUser(form);
			}
		}
		
		return "redirect:/calc/";
	}
	
	
}
