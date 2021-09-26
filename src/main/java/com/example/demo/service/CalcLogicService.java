package com.example.demo.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.entity.DateFormula;
import com.example.demo.entity.Result;

@Service
public class CalcLogicService {
	
	public List<Result> calcLogic(List<DateFormula> dateFormula, LocalDate date) {
		
		List<Result> results = new ArrayList<>();
		
		for (DateFormula data : dateFormula) {
			Result result = new Result();
			LocalDate calcResult = date.plusYears(data.getYear()).plusMonths(data.getMonth()).plusDays(data.getDay());
			result.setCalcResult(calcResult);
			result.setDateFormula(data);
			results.add(result);
		}
		
		return results;
	}
	
}
