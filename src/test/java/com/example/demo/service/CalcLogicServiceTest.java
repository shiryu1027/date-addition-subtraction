package com.example.demo.service;

import static org.assertj.core.api.Assertions.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.entity.DateFormula;
import com.example.demo.entity.Result;

@SpringBootTest
@Transactional
@DisplayName("加減算データと基準日を渡すと計算結果を含んだ加減算データを返す")
class CalcLogicServiceTest {
	
	@Autowired
	CalcLogicService calcLogicService;
	
	@Nested
	class calcDataのsizeが0件のとき {
		
		private List<DateFormula> dateFormula;
		LocalDate recordDate;
		
		@BeforeEach
		void setup() {
			dateFormula = new ArrayList<DateFormula>();
			DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy/MM/dd");
			recordDate = LocalDate.parse("1997/10/27", fmt);
		}
		
		@Test
		void calcResultのsizeは0である() {
			List<Result> calcDataResult = calcLogicService.calcLogic(dateFormula, recordDate);
			assertThat(calcDataResult.size()).isEqualTo(0);
		}
		
	}
	
	@Nested
	class calcDataのsizeが2件のとき {
		
		private List<DateFormula> dateFormula;
		LocalDate recordDate;
		
		@BeforeEach
		void setup() {
			dateFormula = new ArrayList<DateFormula>();
			DateFormula calcData1 = new DateFormula();
			DateFormula calcData2 = new DateFormula();
			calcData1.setDateFormulaCode("+3Y-2M+1D");
			calcData1.setYear(3);
			calcData1.setMonth(-2);
			calcData1.setDay(1);
			calcData2.setDateFormulaCode("-10Y+5M-11D");
			calcData2.setYear(-10);
			calcData2.setMonth(5);
			calcData2.setDay(-11);
			dateFormula.add(calcData1);
			dateFormula.add(calcData2);
			DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy/MM/dd");
			recordDate = LocalDate.parse("1997/10/27", fmt);
		}
		
		@Test
		void calcResultのsizeは2である() {
			List<Result> calcDataResult = calcLogicService.calcLogic(dateFormula, recordDate);
			assertThat(calcDataResult.size()).isEqualTo(2);
		}
		
		@Test
		void calcData条件1の計算結果がplusYearsとminusMonthsを用いた計算と一致する() {
			List<Result> calcDataResult = calcLogicService.calcLogic(dateFormula, recordDate);
			assertThat(calcDataResult.get(0).getCalcResult())
				.isEqualTo(recordDate.plusYears(3).minusMonths(2).plusDays(1));
		}
		
		@Test
		void calcData条件2の計算結果がplusYearsとminusMonthsを用いた計算と一致する() {
			List<Result> calcDataResult = calcLogicService.calcLogic(dateFormula, recordDate);
			assertThat(calcDataResult.get(1).getCalcResult())
				.isEqualTo(recordDate.minusYears(10).plusMonths(5).minusDays(11));
		}
		
	}
}
