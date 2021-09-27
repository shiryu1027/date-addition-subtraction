package com.example.demo.service;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.AppUser;
import com.example.demo.entity.DateFormula;
import com.example.demo.mapper.DateFormulasMapper;

@Service
public class CalcService {
	
	@Autowired
	DateFormulasMapper mapper;
	
	@Autowired
	UserService uService;
	
	/* 計算式を1件取得 */
	public DateFormula getDateFormula(int id) {
		return mapper.calcDataOne(id);
	}
	
	/* ユーザーが持つ加減算用データを全件取得 */
	public List<DateFormula> getFormulas(String mailAddress) {
		return mapper.calcDataAll(mailAddress);
	}
	
	/* 加減算データを新規登録 */
	public void addDateFormula(DateFormula dateFormula, Principal principal) {
		
		/* ユーザー情報取得(加減算データにuser_idを登録するため) */
		AppUser user = uService.getSigninUser(principal.getName());
		
		dateFormula.setUserId(user.getUserId());
		
		/* 新規登録 */
		mapper.calcDataInsert(dateFormula);
	}
	
	/* 加減算用データの更新 */
	public void alterDateFormula(DateFormula dateFormula, Principal principal) {
		
		AppUser user = uService.getSigninUser(principal.getName());
		
		dateFormula.setUserId(user.getUserId());
		
		mapper.calcDataUpdate(dateFormula);
	}
	
	/* 加減算用データの削除 */
	public void deleteDateFormula(int id) {
		mapper.calcDataDelete(id);
	}
	
}
