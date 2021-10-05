package com.example.demo.controller;

import java.security.Principal;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.modelmapper.ModelMapper;
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
import com.example.demo.form.DateFormulaForm;
import com.example.demo.service.AutoDateFormulaCodeService;
import com.example.demo.service.CalcLogicService;
import com.example.demo.service.CalcService;
import com.example.demo.service.UserService;

@Controller
@RequestMapping("/calc")
public class CalcController {
	
	@Autowired
	CalcService calcService;
	
	@Autowired
	UserService userService;
	
	@Autowired
	CalcLogicService calcLogicService;
	
	@Autowired
	ModelMapper modelMapper;
	
	@Autowired
	AutoDateFormulaCodeService autoDateFormulaCodeService;
	
	/* トップページ取得 */
	@GetMapping("/")
	public String index(Principal principal, Model model) {
		
		String mailAddress = principal.getName();
		List<DateFormula> formulas = calcService.getDateFormulas(mailAddress);
		
		model.addAttribute("username", userService.getUsername(mailAddress));
		model.addAttribute("formulas", formulas);
		
		return "calc/index";
	}
	
	/* 基準日から計算結果を算出、トップページ取得 */
	@PostMapping("/result")
	public String result(@Validated @ModelAttribute BaseDateForm form, BindingResult result,
			Principal principal, Model model) {
		
		if(result.hasErrors()) {
			return index(principal, model);
		}
		
		String mailAddress = principal.getName();
		List<DateFormula> formulas = calcService.getDateFormulas(mailAddress);
		List<Result> results = calcLogicService.getCalcResults(formulas, form.getBaseDate());
		
		DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy/MM/dd");
		String baseDate = form.getBaseDate().format(fmt);
		
		model.addAttribute("username", userService.getUsername(mailAddress));
		model.addAttribute("results", results);
		model.addAttribute("baseDate", baseDate);
		return "calc/index";
	}
	
	
	/* 計算式の新規登録画面を表示 */
	@GetMapping("/add")
	public String addDisplay(@ModelAttribute DateFormulaForm form, Principal principal, Model model) {
		
		String mailAddress = principal.getName();
		model.addAttribute("username", userService.getUsername(mailAddress));
		
		return "calc/add";
	}
	
	/* 加減算用データをDBに新規登録 */
	@PostMapping("/add")
	public String add(@Validated @ModelAttribute DateFormulaForm form, BindingResult result, 
			Principal principal, Model model) {
		
		if (result.hasErrors()) {
			return addDisplay(form, principal, model);
		}
		
		DateFormula dateFormula = modelMapper.map(form, DateFormula.class); // modelMapperはForm->Entityへの変換
		autoDateFormulaCodeService.autoDateFormulaCode(dateFormula);
		calcService.addDateFormula(dateFormula, principal);
		return "redirect:/calc/";
	}
	
	
	/* 加減算用データを更新データ画面を表示 */
	@GetMapping("/alter/id={id}")
	public String alterDisplay(@PathVariable("id") int id, @ModelAttribute DateFormulaForm form, 
			Principal principal, Model model) {
		
		String mailAddress = principal.getName();
		DateFormula dateFormula = calcService.getDateFormula(id);
		
		model.addAttribute("username", userService.getUsername(mailAddress));
		model.addAttribute("dateFormula", dateFormula);
		return "calc/alter";
	}
	
	/* 加減算用データを更新 */
	@PostMapping("/alter/id={id}/post")
	public String alter(@PathVariable("id") int id, @Validated @ModelAttribute DateFormulaForm form, BindingResult result, 
			Principal principal, Model model) {
		if (result.hasErrors()) {
			System.out.println("ここ");
			return alterDisplay(id, form, principal, model);
		}
		
		DateFormula dateFormula = modelMapper.map(form, DateFormula.class);
		dateFormula.setDateFormulaId(id);
		
		autoDateFormulaCodeService.autoDateFormulaCode(dateFormula);
		calcService.alterDateFormula(dateFormula, principal);
		
		return "redirect:/calc/";
	}
	
	
	/* 加減算用データの削除 */
	@PostMapping("/delete/id={id}")
	public String delete(@PathVariable("id") int id, Principal principal, Model model) {
		
		calcService.deleteDateFormula(id);
		
		return "redirect:/calc/";
	}
	
	
}
