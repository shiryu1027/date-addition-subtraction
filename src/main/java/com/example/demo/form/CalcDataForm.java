package com.example.demo.form;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

import lombok.Data;

@Data
public class CalcDataForm {
	
	private int additionSubtractionId;
	
	private String additionSubtractionCode;
	
	@Max(value=100000, message="100,000以下の値を入力して下さい")
	@Min(value=-100000, message="-100,000以上の値を入力して下さい")
	private int year;
	/* LocalDateのYearは10億年を超えると、エラーが起きる(10万年でも多すぎるくらい) */
	
	@Max(value=100000, message="100,000以下の値を入力して下さい")
	@Min(value=-100000, message="-100,000以上の値を入力して下さい")
	private int month;
	
	@Max(value=100000, message="100,000以下の値を入力して下さい")
	@Min(value=-100000, message="-100,000以上の値を入力して下さい")
	private int day;
	
	private String explanation;
	
	private int userId;
	
}
