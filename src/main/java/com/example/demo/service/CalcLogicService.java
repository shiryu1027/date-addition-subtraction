package com.example.demo.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.entity.CalcData;

@Service
public class CalcLogicService {
	
	public List<CalcData> calcLogic(List<CalcData> calcData, LocalDate date) {
		
		/* 計算結果を格納するListを作成 */
		List<CalcData> calcDataResult = new ArrayList<>();
		
		/* dateを用いて、計算結果を算出 */
		for (CalcData data : calcData) {
			
			/* 計算結果算出 */
			LocalDate result = date.plusYears(data.getYear()).plusMonths(data.getMonth()).plusDays(data.getDay());
			
			/* dataのcalcResultフィールドに代入*/
			data.setCalcResult(result);
			
			/* 計算結果を格納するListにdataを代入 */
			calcDataResult.add(data);
		}
		
		return calcDataResult;
	}
	
}
