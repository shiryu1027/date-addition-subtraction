package com.example.demo.entity;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import lombok.Data;

@Data
public class Result {
	
	private DateFormula dateFormula;
	
	private LocalDate calcResult;
	
	/* dateFormulaのgetterは使えないので、中身のgetterを作成 */
	public int getAdditionSubtractionId() {
		return this.dateFormula.getDateFormulaId();
	}
	
	public String getAdditionSubtractionCode () {
		return this.dateFormula.getDateFormulaCode();
	}
	
	public int getYear() {
		return this.dateFormula.getYear();
	}
	
	public int getMonth() {
		return this.dateFormula.getMonth();
	}
	
	public int getDay() {
		return this.dateFormula.getDay();
	}
	
	public String getExplanation() {
		return this.dateFormula.getExplanation();
	}
	
	public int getUserId() {
		return this.dateFormula.getUserId();
	}
	
	public String getCalcResult() {
		DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy/MM/dd");
		return this.calcResult.format(fmt);
	}
}
