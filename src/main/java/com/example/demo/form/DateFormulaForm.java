package com.example.demo.form;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class DateFormulaForm {
	
	private int dateFormulaId;
	
	private String dateFormulaCode;
	
	@Max(value=2500, message="年には2,500以下の値を入力して下さい")
	@Min(value=-2500, message="年には-2,500以上の値を入力して下さい")
	private int year;
	/* LocalDateのYearは10億年を超えると、エラーが起きる(10万年でも多すぎるくらい) */
	
	@Max(value=2500, message="月には2,500以下の値を入力して下さい")
	@Min(value=-2500, message="月には-2,500以上の値を入力して下さい")
	private int month;
	
	@Max(value=2500, message="日には2,500以下の値を入力して下さい")
	@Min(value=-2500, message="日には-2,500以上の値を入力して下さい")
	private int day;
	
	@NotBlank(message="説明は必須項目です")
	private String explanation;
	
	private int userId;
	
	public DateFormulaForm() {
		
	}
	
	public DateFormulaForm(int dateFormulaId, String dateFormulaCode, int year, int month, int day, String explanation, int userId) {
		this.dateFormulaId = dateFormulaId;
		this.dateFormulaCode = dateFormulaCode;
		this.year = year;
		this.month = month;
		this.day = day;
		this.explanation = explanation;
		this.userId = userId;
	}
}
