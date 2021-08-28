package com.example.demo.controller;

import java.security.Principal;
import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.entity.CalcData;
import com.example.demo.form.CalcDataForm;
import com.example.demo.service.CalculationLogicService;
import com.example.demo.service.CalculationService;

@Controller
@RequestMapping("calc")
public class CalculationController {
	
	@Autowired
	CalculationService service;
	
	@Autowired
	CalculationLogicService logicService;
	
	/* ホームぺージ取得 */
	@GetMapping("/")
	public String index(Principal principal, Model model) { // Principalはサインイン情報を格納している
		
		/* サインイン情報のIDを取得 */
		String name = principal.getName();
		
		/* 加減算用データを取得 */
		List<CalcData> calcList = service.calcDataAll(name);
		
		model.addAttribute("name", name);
		model.addAttribute("calcList", calcList);
		
		return "calc/index";
	}
	
	/* 加減算用データを新規登録 */
	@PostMapping("/insert")
	public String insert(@ModelAttribute CalcDataForm form, Principal principal) {
		
		service.calcDataInsert(form, principal);
		
		return "redirect:/calc/";
	}
	
	/* 計算用基準日を指定、その後計算実行 */
	@PostMapping("/result")
	public String result(@RequestParam("date") LocalDate date, Principal principal, Model model) {
		
		/* サインイン情報のIDを取得 */
		String name = principal.getName();
		
		/* 加減算用データを取得 */
		List<CalcData> calcList = service.calcDataAll(name);
		
		/* 全てのデータに対して、計算を適用 */
		List<CalcData> calcListResult = logicService.calcLogic(calcList, date);
		
		model.addAttribute("results", calcListResult);
		
		model.addAttribute("name", name);
		
		return "calc/index";
	}
	
}
