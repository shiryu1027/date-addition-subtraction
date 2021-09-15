package com.example.demo.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.entity.CalcData;

@Service
public class CalcLogicService {
	
	public List<CalcData> calcLogic(List<CalcData> calcData, LocalDate date) {
		
		List<CalcData> calcDataResult = new ArrayList<>();
		
		for (CalcData data : calcData) {
			LocalDate result = date.plusYears(data.getYear()).plusMonths(data.getMonth()).plusDays(data.getDay());
			data.setCalcResult(result);
			calcDataResult.add(data);
		}
		
		return calcDataResult;
	}
	
}
