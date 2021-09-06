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
public class CalcService {
	
	@Autowired
	AdditionSubtractionMapper mapper;
	
	@Autowired
	UsersService uService;
	
	/* 加減算用データを取得(1件) */
	public CalcData calcDataOne(int id) {
		return mapper.calcDataOne(id);
	}
	
	/* ユーザーが持つ加減算用データを取得 */
	public List<CalcData> getCalcDataAll(String mailAddress) {
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
	
	/* 加減算用データの更新 */
	public void calcDataUpdate(CalcDataForm form, Principal principal) {
		
		AppUser user = uService.getSignInUser(principal.getName());
		
		form.setUserId(user.getUserId());
		
		mapper.calcDataUpdate(form);
	}
	
	/* 加減算用データの削除 */
	public void calcDataDelete(int id) {
		mapper.calcDataDelete(id);
	}
	
}
