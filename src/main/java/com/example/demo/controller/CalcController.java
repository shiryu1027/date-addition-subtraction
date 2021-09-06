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

import com.example.demo.entity.CalcData;
import com.example.demo.form.CalcDataForm;
import com.example.demo.form.RecordDateForm;
import com.example.demo.service.AutoAdditionSubtractionCodeService;
import com.example.demo.service.CalcService;
import com.example.demo.service.CalcLogicService;
import com.example.demo.service.UsersService;

@Controller
@RequestMapping("calc")
public class CalcController {
	
	@Autowired
	CalcService calcService;
	
	@Autowired
	UsersService usersService;
	
	@Autowired
	CalcLogicService logicService;
	
	@Autowired
	AutoAdditionSubtractionCodeService autoAdditionSubtractionCodeService;
	
	/* ホームぺージ取得 */
	@GetMapping("/")
	public String index(Principal principal, Model model) {
		
		String userMailAddress = principal.getName();
		
		String nickname = usersService.getUserNickname(userMailAddress);
		model.addAttribute("nickname", nickname);
		
		List<CalcData> calcList = calcService.getCalcDataAll(userMailAddress);
		model.addAttribute("calcList", calcList);
		
		return "calc/index";
	}
	
	/* 計算用基準日を指定、その後計算実行 */
	@PostMapping("/result")
	public String result(@Validated @ModelAttribute RecordDateForm form, BindingResult result, Principal principal, Model model) {
		
		if(result.hasErrors()) {
			return index(principal, model);
		}
		
		String UserMailAddress = principal.getName();
		
		String nickname = usersService.getUserNickname(UserMailAddress);
		model.addAttribute("nickname", nickname);
		
		List<CalcData> calcList = calcService.getCalcDataAll(UserMailAddress);
		
		/* 全てのデータに対して、計算を適用 */
		List<CalcData> calcListResult = logicService.calcLogic(calcList, form.getRecordDate());
		model.addAttribute("results", calcListResult);
		
		model.addAttribute("referenceDate", form.getRecordDate());
		
		return "calc/index";
	}
	
	
	/* 加減算用データの新規登録画面を表示 */
	@GetMapping("/insert")
	public String insertDisplay(@ModelAttribute CalcDataForm form, Principal principal, Model model) {
		
		String userMailAddress = principal.getName();
		
		String nickname = usersService.getUserNickname(userMailAddress);
		model.addAttribute("nickname", nickname);
		
		return "calc/insert";
	}
	
	/* 加減算用データをDBに新規登録 */
	@PostMapping("/insert")
	public String insert(@Validated @ModelAttribute CalcDataForm form, BindingResult result, Principal principal, Model model) {
		
		if (result.hasErrors()) {
			return insertDisplay(form, principal, model); // formにエラーメッセージ格納
		}
		
		autoAdditionSubtractionCodeService.autoAdditionSubtractionCode(form);
		
		calcService.calcDataInsert(form, principal);
		
		return "redirect:/calc/";
	}
	
	
	/* 加減算用データを更新データ画面を表示 */
	@GetMapping("/update/id={id}")
	public String updateDisplay(@PathVariable("id") int id, @ModelAttribute CalcDataForm form, 
		Principal principal, Model model) {
		
		String UserMailAddress = principal.getName();
		
		String nickname = usersService.getUserNickname(UserMailAddress);
		model.addAttribute("nickname", nickname);
		
		/* 加減算データ1件取得 */
		CalcData calcData = calcService.calcDataOne(id);
		model.addAttribute("calcData", calcData);
		
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
		
		calcService.calcDataUpdate(form, principal);
		
		return "redirect:/calc/";
	}
	
	
	/* 加減算用データの削除 */
	@PostMapping("/delete/id={id}")
	public String delete(@PathVariable("id") int id, Principal principal, Model model) {
		
		calcService.calcDataDelete(id);
		
		return "redirect:/calc/";
	}
	
	
}
