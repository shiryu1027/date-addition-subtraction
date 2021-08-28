package com.example.demo.service;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.AppUser;
import com.example.demo.entity.CalcData;
import com.example.demo.form.CalcDataForm;
import com.example.demo.mapper.AdditionSubtractionMapper;

@Service
public class CalculationService {
	
	@Autowired
	AdditionSubtractionMapper mapper;
	
	@Autowired
	UsersService uService; 
	
	/* ユーザーが持つ加減算用データを取得 */
	public List<CalcData> calcDataAll(String mailAddress) {
		return mapper.calcDataAll(mailAddress);
	}
	
	/* 加減算データを新規登録 */
	public void calcDataInsert(CalcDataForm form, Principal principal) {
		
		/* ユーザー情報取得(加減算データにuser_idを登録するため) */
		AppUser user = uService.getSignInUser(principal.getName());
		
		/* formにuser_idをセット */
		form.setUserId(user.getUserId());
		
		/* 新規登録 */
		mapper.calcDataInsert(form);
	}
	
}
