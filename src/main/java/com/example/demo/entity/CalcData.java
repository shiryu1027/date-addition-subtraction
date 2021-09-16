package com.example.demo.entity;

import java.time.LocalDate;

import lombok.Data;

@Data
public class CalcData {
	
	private int additionSubtractionId;
	
	private String additionSubtractionCode;
	
	private int year;
	
	private int month;
	
	private int day;
	
	private String explanation;
	
	private int userId;
	
	private LocalDate calcResult;
	
	public CalcData() {
		
	}
	
	public CalcData(int additionSubtractionId, String additionSubtractionCode, int year, int month, int day, String explanation, int userId) {
		this.additionSubtractionId = additionSubtractionId;
		this.additionSubtractionCode = additionSubtractionCode;
		this.year = year;
		this.month = month;
		this.day = day;
		this.explanation = explanation;
		this.userId = userId;
	}
	
}
