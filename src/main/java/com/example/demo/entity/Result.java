package com.example.demo.entity;

import java.time.LocalDate;

import lombok.Data;

@Data
public class Result {
	
	private DateFormula dateFormula;
	
	private LocalDate calcResult;
}
