package com.example.demo.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.entity.DateFormula;
import com.example.demo.entity.Result;
import com.example.demo.form.BaseDateForm;
import com.example.demo.form.CalcDataForm;
import com.example.demo.service.AutoAdditionSubtractionCodeService;
import com.example.demo.service.CalcLogicService;
import com.example.demo.service.CalcService;
import com.example.demo.service.UserService;

@Controller
@RequestMapping("calc")
public class CalcController {
	
	@Autowired
	CalcService calcService;
	
	@Autowired
	UserService userService;
	
	@Autowired
	CalcLogicService logicService;
	
	@Autowired
	AutoAdditionSubtractionCodeService autoAdditionSubtractionCodeService;
	
	/* トップページ取得 */
	@GetMapping("/")
	public String index(Principal principal, Model model) {
		
		String mailAddress = principal.getName();
		List<DateFormula> formulas = calcService.getFormulas(mailAddress);
		
		model.addAttribute("username", userService.getUsername(mailAddress));
		model.addAttribute("formulas", formulas);
		
		return "calc/index";
	}
	
	/* 基準日から計算結果を算出、トップページ取得 */
	@PostMapping("/result")
	public String result(@Validated @ModelAttribute BaseDateForm form, BindingResult result, Principal principal, Model model) {
		
		if(result.hasErrors()) {
			return index(principal, model);
		}
		
		String mailAddress = principal.getName();
		
		List<DateFormula> formulas = calcService.getFormulas(mailAddress);
		List<Result> results = logicService.calcLogic(formulas, form.getBaseDate());
		
		model.addAttribute("username", userService.getUsername(mailAddress));
		model.addAttribute("results", results);
		// Resultの中身が隠されているせいでアクセス出来ていない
		model.addAttribute("baseDate", form.getBaseDate());
		
		return "calc/index";
	}
	
	
	/* 計算式の新規登録画面を表示 */
	@GetMapping("/add")
	public String addDisplay(@ModelAttribute CalcDataForm form, Principal principal, Model model) {
		
		String mailAddress = principal.getName();
		
		model.addAttribute("username", userService.getUsername(mailAddress));
		
		return "calc/add";
	}
	
	/* 加減算用データをDBに新規登録 */
	@PostMapping("/add")
	public String add(@Validated @ModelAttribute CalcDataForm form, BindingResult result, 
		Principal principal, Model model) {
		
		if (result.hasErrors()) {
			return addDisplay(form, principal, model);
		}
		
		autoAdditionSubtractionCodeService.autoAdditionSubtractionCode(form);
		
		calcService.addDateFormula(form, principal);
		
		return "redirect:/calc/";
	}
	
	
	/* 加減算用データを更新データ画面を表示 */
	@GetMapping("/update/id={id}")
	public String updateDisplay(@PathVariable("id") int id, @ModelAttribute CalcDataForm form, 
		Principal principal, Model model) {
		
		String mailAddress = principal.getName();
		
		DateFormula dateFormula = calcService.getDateFormula(id);
		
		model.addAttribute("username", userService.getUsername(mailAddress));
		model.addAttribute("calcData", dateFormula);
		
		return "/calc/update";
	}
	
	/* 加減算用データを更新 */
	@PostMapping("/update/id={id}/post")
	public String update(@PathVariable("id") int id, @Validated @ModelAttribute CalcDataForm form, BindingResult result, 
		Principal principal, Model model) {
		
		if (result.hasErrors()) {
			return updateDisplay(id, form, principal, model);
		}
		
		form.setAdditionSubtractionId(id);
		autoAdditionSubtractionCodeService.autoAdditionSubtractionCode(form);
		
		calcService.alterDateFormula(form, principal);
		
		return "redirect:/calc/";
	}
	
	
	/* 加減算用データの削除 */
	@PostMapping("/delete/id={id}")
	public String delete(@PathVariable("id") int id, Principal principal, Model model) {
		
		calcService.deleteDateFormula(id);
		
		return "redirect:/calc/";
	}
	
	
}
