package com.example.demo.service;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.demo.form.DateFormulaForm;

@SpringBootTest
@DisplayName("AutoAdditionSubtractionCodeServiceのテスト")
class AutoAdditionSubtractionCodeServiceTest {
	
	DateFormulaForm dateFormulaForm;
	String actual;
	
	@Autowired
	AutoAdditionSubtractionCodeService target;
	
	@Nested
	class formの値がYear_Month_Day_100000_minus100000_0のとき {
		@BeforeEach
		void setup() {
			dateFormulaForm = new DateFormulaForm();
			dateFormulaForm.setYear(100000);
			dateFormulaForm.setMonth(-100000);
			dateFormulaForm.setDay(0);
			actual = target.autoAdditionSubtractionCode(dateFormulaForm).getAdditionSubtractionCode();
		}
		
		@Test
		void additionSubtractionCodeはplus1000000Yminus100000Mである() {
			assertThat(actual).isEqualTo("+100000Y-100000M");
		}
	}
	
	@Nested
	class formの値がYear_Month_Day_minus100000_0_100000のとき {
		@BeforeEach
		void setup() {
			dateFormulaForm = new DateFormulaForm();
			dateFormulaForm.setYear(-100000);
			dateFormulaForm.setMonth(0);
			dateFormulaForm.setDay(100000);
			actual = target.autoAdditionSubtractionCode(dateFormulaForm).getAdditionSubtractionCode();
		}
		
		@Test
		void additionSubtractionCodeはminus1000000Yplus100000Dである() {
			assertThat(actual).isEqualTo("-100000Y+100000D");
		}
	}
	
	@Nested
	class formの値がYear_Month_Day_0_0_0のとき {
		@BeforeEach
		void setup() {
			dateFormulaForm = new DateFormulaForm();
			dateFormulaForm.setYear(0);
			dateFormulaForm.setMonth(0);
			dateFormulaForm.setDay(0);
			actual = target.autoAdditionSubtractionCode(dateFormulaForm).getAdditionSubtractionCode();
		}
		
		@Test
		void additionSubtractionCodeは_0YMD_である() {
			assertThat(actual).isEqualTo("0YMD");
		}
	}
}

