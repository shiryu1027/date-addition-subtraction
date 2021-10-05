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
	class dateFormulasのsizeが0件のとき {
		
		private List<DateFormula> dateFormulas;
		LocalDate baseDate;
		
		@BeforeEach
		void setup() {
			dateFormulas = new ArrayList<DateFormula>();
			DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy/MM/dd");
			baseDate = LocalDate.parse("1997/10/27", fmt);
		}
		
		@Test
		void calcResultのsizeは0である() {
			List<Result> calcResults = calcLogicService.getCalcResults(dateFormulas, baseDate);
			assertThat(calcResults.size()).isEqualTo(0);
		}
		
	}
	
	@Nested
	class dateFormulasのsizeが2件のとき {
		
		private List<DateFormula> dateFormulas;
		LocalDate baseDate;
		private DateTimeFormatter fmt;
		
		@BeforeEach
		void setup() {
			dateFormulas = new ArrayList<DateFormula>();
			DateFormula dateFormula1 = new DateFormula();
			DateFormula dateFormula2 = new DateFormula();
			dateFormula1.setDateFormulaCode("+3Y-2M+1D");
			dateFormula1.setYear(3);
			dateFormula1.setMonth(-2);
			dateFormula1.setDay(1);
			dateFormula2.setDateFormulaCode("-10Y+5M-11D");
			dateFormula2.setYear(-10);
			dateFormula2.setMonth(5);
			dateFormula2.setDay(-11);
			dateFormulas.add(dateFormula1);
			dateFormulas.add(dateFormula2);
			fmt = DateTimeFormatter.ofPattern("yyyy/MM/dd");
			baseDate = LocalDate.parse("1997/10/27", fmt);
		}
		
		@Test
		void calcResultのsizeは2である() {
			List<Result> calcResult = calcLogicService.getCalcResults(dateFormulas, baseDate);
			assertThat(calcResult.size()).isEqualTo(2);
		}
		
		@Test
		void dateFormula条件1の計算結果がplusYearsとminusMonthsを用いた計算と一致する() {
			List<Result> calcResult = calcLogicService.getCalcResults(dateFormulas, baseDate);
			assertThat(calcResult.get(0).getCalcResult())
				.isEqualTo(baseDate.plusYears(3).minusMonths(2).plusDays(1).format(fmt));
		}
		
		@Test
		void dateFormula条件2の計算結果がplusYearsとminusMonthsを用いた計算と一致する() {
			List<Result> calcResult = calcLogicService.getCalcResults(dateFormulas, baseDate);
			assertThat(calcResult.get(1).getCalcResult())
				.isEqualTo(baseDate.minusYears(10).plusMonths(5).minusDays(11).format(fmt));
		}
		
	}
}
