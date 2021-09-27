package com.example.demo.entity;

import lombok.Data;

@Data
public class DateFormula {
	
	private int dateFormulaId;
	
	private String dateFormulaCode;
	
	private int year;
	
	private int month;
	
	private int day;
	
	private String explanation;
	
	private int userId;
	
	public DateFormula() {
		
	}
	
	public DateFormula(int dateFormulaId, String dateFormulaCode, int year, int month, int day, String explanation, int userId) {
		this.dateFormulaId = dateFormulaId;
		this.dateFormulaCode = dateFormulaCode;
		this.year = year;
		this.month = month;
		this.day = day;
		this.explanation = explanation;
		this.userId = userId;
	}
	
}
