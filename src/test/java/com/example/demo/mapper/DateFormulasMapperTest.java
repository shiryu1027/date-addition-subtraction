package com.example.demo.mapper;

import static org.assertj.core.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.entity.DateFormula;

@SpringBootTest
@MybatisTest
@Transactional
@TestPropertySource(locations = "classpath:test.properties")
class DateFormulasMapperTest {
	
	@Autowired
	DateFormulasMapper dateFormulasMapper;
	
	@Autowired
	private NamedParameterJdbcOperations namedParameterJdbcOperations;
	
	@Test
	void 計算式IDから計算式を一件取得() throws Exception{
		DateFormula dateFormula = dateFormulasMapper.selectDateFormula(2);
		
		assertThat(dateFormula.getDateFormulaId()).isEqualTo(2);
		assertThat(dateFormula.getDateFormulaCode()).isEqualTo("+3Y+4M");
		assertThat(dateFormula.getYear()).isEqualTo(3);
		assertThat(dateFormula.getMonth()).isEqualTo(4);
		assertThat(dateFormula.getDay()).isEqualTo(0);
		assertThat(dateFormula.getExplanation()).isEqualTo("3年と4か月後");
		assertThat(dateFormula.getUserId()).isEqualTo(1);
	}
	
	@Test
	void メールアドレスからユーザの計算式を全件取得() throws Exception {
		List<DateFormula> dateFormulas = dateFormulasMapper.selectDateFormulas("user@gmail.com");
		assertThat(dateFormulas.size()).isEqualTo(2);
		assertThat(dateFormulas.get(0).getDateFormulaCode()).isEqualTo("+3Y+4M");
		assertThat(dateFormulas.get(1).getDateFormulaCode()).isEqualTo("+1Y");
	}
	
	@Test
	void 計算式の新規登録() throws Exception {
		DateFormula dateFormula = new DateFormula();
		dateFormula.setDateFormulaCode("+7Y");
		dateFormula.setYear(7);
		dateFormula.setMonth(0);
		dateFormula.setDay(0);
		dateFormula.setExplanation("7年後");
		dateFormula.setUserId(1);
		
		dateFormulasMapper.insertDateFormula(dateFormula);
		
		DateFormula actualFormula = 
				namedParameterJdbcOperations.queryForObject("SELECT * FROM date_formulas WHERE date_formula_id=:id",
						new MapSqlParameterSource("id", 4),
						new BeanPropertyRowMapper<>(DateFormula.class));
		
		assertThat(actualFormula.getDateFormulaCode()).isEqualTo("+7Y");
		assertThat(actualFormula.getUserId()).isEqualTo(1);
	}
	
	@Test
	void 計算式IDから計算式を削除() throws Exception {
		dateFormulasMapper.deleteDateFormula(2);
		
		List<DateFormula> actualFormulas = 
				namedParameterJdbcOperations.query("SELECT * FROM date_formulas WHERE user_id="
						+ "(SELECT user_id FROM users WHERE mail_address=:mailAddress)"
						+ "ORDER BY date_formula_id DESC",
						new MapSqlParameterSource("mailAddress", "user@gmail.com"),
						new BeanPropertyRowMapper<>(DateFormula.class));
		
		assertThat(actualFormulas.size()).isEqualTo(1);
		assertThat(actualFormulas.get(0).getDateFormulaCode()).isEqualTo("+1Y");
	}

}
