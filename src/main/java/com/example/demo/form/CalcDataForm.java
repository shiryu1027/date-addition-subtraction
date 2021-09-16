package com.example.demo.form;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class CalcDataForm {
	
	private int additionSubtractionId;
	
	private String additionSubtractionCode;
	
	@Max(value=100000, message="年には100,000以下の値を入力して下さい")
	@Min(value=-100000, message="年には-100,000以上の値を入力して下さい")
	private int year;
	/* LocalDateのYearは10億年を超えると、エラーが起きる(10万年でも多すぎるくらい) */
	
	@Max(value=100000, message="月には100,000以下の値を入力して下さい")
	@Min(value=-100000, message="月には-100,000以上の値を入力して下さい")
	private int month;
	
	@Max(value=100000, message="日には100,000以下の値を入力して下さい")
	@Min(value=-100000, message="日には-100,000以上の値を入力して下さい")
	private int day;
	
	@NotBlank(message="説明は必須項目です")
	private String explanation;
	
	private int userId;
	
	public CalcDataForm() {
		
	}
	
	public CalcDataForm(int additionSubtractionId, String additionSubtractionCode, int year, int month, int day, String explanation, int userId) {
		this.additionSubtractionId = additionSubtractionId;
		this.additionSubtractionCode = additionSubtractionCode;
		this.year = year;
		this.month = month;
		this.day = day;
		this.explanation = explanation;
		this.userId = userId;
	}
}
