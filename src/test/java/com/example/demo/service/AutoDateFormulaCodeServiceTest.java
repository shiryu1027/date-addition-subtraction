package com.example.demo.service;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.demo.entity.DateFormula;

@SpringBootTest
@DisplayName("AutoDateFormulaCodeServiceのテスト")
class AutoDateFormulaCodeServiceTest {
	
	DateFormula dateFormula;
	String actual;
	
	@Autowired
	AutoDateFormulaCodeService sut;
	
	@Nested
	class dateFormulaの値がYear_Month_Day_100000_minus100000_0のとき {
		@BeforeEach
		void setup() {
			dateFormula = new DateFormula();
			dateFormula.setYear(100000);
			dateFormula.setMonth(-100000);
			dateFormula.setDay(0);
			actual = sut.autoDateFormulaCode(dateFormula).getDateFormulaCode();
		}
		
		@Test
		void dateFormulaCodeはplus1000000Yminus100000Mである() {
			assertThat(actual).isEqualTo("+100000Y-100000M");
		}
	}
	
	@Nested
	class dateFormulaの値がYear_Month_Day_minus100000_0_100000のとき {
		@BeforeEach
		void setup() {
			dateFormula = new DateFormula();
			dateFormula.setYear(-100000);
			dateFormula.setMonth(0);
			dateFormula.setDay(100000);
			actual = sut.autoDateFormulaCode(dateFormula).getDateFormulaCode();
		}
		
		@Test
		void dateFormulaCodeはminus1000000Yplus100000Dである() {
			assertThat(actual).isEqualTo("-100000Y+100000D");
		}
	}
	
	@Nested
	class dateFormulaの値がYear_Month_Day_0_0_0のとき {
		@BeforeEach
		void setup() {
			dateFormula = new DateFormula();
			dateFormula.setYear(0);
			dateFormula.setMonth(0);
			dateFormula.setDay(0);
			actual = sut.autoDateFormulaCode(dateFormula).getDateFormulaCode();
		}
		
		@Test
		void dateFormulaCodeは_0YMD_である() {
			assertThat(actual).isEqualTo("0YMD");
		}
	}
}

