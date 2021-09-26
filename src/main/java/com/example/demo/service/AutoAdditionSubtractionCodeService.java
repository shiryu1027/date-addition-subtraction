package com.example.demo.service;

import org.springframework.stereotype.Service;

import com.example.demo.entity.DateFormula;

@Service
public class AutoAdditionSubtractionCodeService {
	
	public DateFormula autoAdditionSubtractionCode(DateFormula dateFormula) {
		
		int year = dateFormula.getYear();
		int month = dateFormula.getMonth();
		int day = dateFormula.getDay();
		
		String strYear = String.format("%1$+d", year) + "Y";
		String strMonth = String.format("%1$+d", month) + "M";
		String strDay = String.format("%1$+d", day) + "D";
		
		String[] list = new String[3];
		list[0] = strYear;
		list[1] = strMonth;
		list[2] = strDay;
		
		String additionSubtractionCode = "";
		for (String data: list) {
			if (!(data.equals("+0Y") || data.equals("+0M") || data.equals("+0D"))) {
				additionSubtractionCode = additionSubtractionCode + data;
			}
		}
		
		if (additionSubtractionCode.equals("")) {
			additionSubtractionCode = "0YMD";
		}
		
		dateFormula.setDateFormulaCode(additionSubtractionCode);
		
		return dateFormula;
	}
	
}
