package com.example.demo.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.entity.DateFormula;
import com.example.demo.entity.Result;

@Service
public class CalcLogicService {
	
	public List<Result> getCalcResults(List<DateFormula> dateFormulas, LocalDate baseDate) {
		
		List<Result> results = new ArrayList<>();
		
		for (DateFormula dateFormula : dateFormulas) {
			Result result = new Result();
			LocalDate calcResult = 
					baseDate.plusYears(dateFormula.getYear()).plusMonths(dateFormula.getMonth()).plusDays(dateFormula.getDay());
			result.setCalcResult(calcResult);
			result.setDateFormula(dateFormula);
			results.add(result);
		}
		return results;
	}
	
}
