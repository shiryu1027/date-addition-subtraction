package com.example.demo.entity;

import java.time.LocalDate;

import lombok.Data;

@Data
public class CalcData {
	
	private int additionSubtractionId;
	
	private String nickname;
	
	private int year;
	
	private int month;
	
	private int day;
	
	private String explanation;
	
	private int userId;
	
	private LocalDate calcResult;
	
}
