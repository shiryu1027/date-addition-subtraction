package com.example.demo.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.demo.entity.CalcData;
import com.example.demo.form.CalcDataForm;

@Mapper
public interface AdditionSubtractionMapper {
	
	/* 加減算データ一件取得 */
	public CalcData calcDataOne(int id);
	
	/* サインインユーザーの加減算データ取得 */
	List<CalcData> calcDataAll(String mailAddress);
	
	/* 加減算データの新規登録 */
	void calcDataInsert(CalcDataForm form);
	
	/* 加減算用データの更新 */
	public void calcDataUpdate(CalcDataForm form);
	
}
