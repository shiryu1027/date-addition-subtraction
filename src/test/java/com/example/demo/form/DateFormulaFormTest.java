package com.example.demo.form;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

@SpringBootTest
class DateFormulaFormTest{
	
	@Autowired
	Validator validator;
	
	private DateFormulaForm testDateFormulaForm = new DateFormulaForm();
    private BindingResult bindingResult = new BindException(testDateFormulaForm, "DateFormulaForm");

	@Nested
	class 年のチェック {
		
		@BeforeEach
		void setup() {
			testDateFormulaForm.setExplanation("説明文"); // @NotBlankを回避するため
		}
		
		@ParameterizedTest
        @ValueSource(ints = {2500, 2499, -2499, -2500})
    	void 正常系(int year) throws Exception{
        	testDateFormulaForm.setYear(year);
            // テスト実施
            validator.validate(testDateFormulaForm, bindingResult);
            // 結果検証
            assertThat(bindingResult.getFieldError()).isNull();
    	}
		
		@ParameterizedTest
        @ValueSource(ints = {2501, 2502})
		void 異常系_値範囲不正_Max(int year) throws Exception{
			testDateFormulaForm.setYear(year);
            // テスト実施
            validator.validate(testDateFormulaForm, bindingResult);
            // 結果検証
            assertThat(bindingResult.getFieldError().toString())
            	.contains("年には2,500以下の値を入力して下さい");
		}
		
		@ParameterizedTest
        @ValueSource(ints = {-2501, -2502})
		void 異常系_値範囲不正_Min(int year) throws Exception{
			testDateFormulaForm.setYear(year);
            // テスト実施
            validator.validate(testDateFormulaForm, bindingResult);
            // 結果検証
            assertThat(bindingResult.getFieldError().toString())
            	.contains("年には-2,500以上の値を入力して下さい");
		}
	}
	
	@Nested
	class 月のチェック {
		
		@BeforeEach
		void setup() {
			testDateFormulaForm.setExplanation("説明文");
		}
		
		@ParameterizedTest
        @ValueSource(ints = {2500, 2499, -2499, -2500})
    	void 正常系(int month) throws Exception{
        	testDateFormulaForm.setMonth(month);
            // テスト実施
            validator.validate(testDateFormulaForm, bindingResult);
            // 結果検証
            assertThat(bindingResult.getFieldError()).isNull();
    	}
		
		@ParameterizedTest
        @ValueSource(ints = {2501, 2502})
		void 異常系_値範囲不正_Max(int month) throws Exception{
			testDateFormulaForm.setMonth(month);
            // テスト実施
            validator.validate(testDateFormulaForm, bindingResult);
            // 結果検証
            assertThat(bindingResult.getFieldError().toString())
            	.contains("月には2,500以下の値を入力して下さい");
		}
		
		@ParameterizedTest
        @ValueSource(ints = {-2501, -2502})
		void 異常系_値範囲不正_Min(int month) throws Exception{
			testDateFormulaForm.setMonth(month);
            // テスト実施
            validator.validate(testDateFormulaForm, bindingResult);
            // 結果検証
            assertThat(bindingResult.getFieldError().toString())
            	.contains("月には-2,500以上の値を入力して下さい");
		}
	}
	
	@Nested
	class 日のチェック {
		
		@BeforeEach
		void setup() {
			testDateFormulaForm.setExplanation("説明文");
		}
		
		@ParameterizedTest
        @ValueSource(ints = {2500, 2499, -2499, -2500})
    	void 正常系(int day) throws Exception{
        	testDateFormulaForm.setDay(day);
            // テスト実施
            validator.validate(testDateFormulaForm, bindingResult);
            // 結果検証
            assertThat(bindingResult.getFieldError()).isNull();
    	}
		
		@ParameterizedTest
        @ValueSource(ints = {2501, 2502})
		void 異常系_値範囲不正_Max(int day) throws Exception{
			testDateFormulaForm.setDay(day);
            // テスト実施
            validator.validate(testDateFormulaForm, bindingResult);
            // 結果検証
            assertThat(bindingResult.getFieldError().toString())
            	.contains("日には2,500以下の値を入力して下さい");
		}
		
		@ParameterizedTest
        @ValueSource(ints = {-2501, -2502})
		void 異常系_値範囲不正_Min(int day) throws Exception{
			testDateFormulaForm.setDay(day);
            // テスト実施
            validator.validate(testDateFormulaForm, bindingResult);
            // 結果検証
            assertThat(bindingResult.getFieldError().toString())
            	.contains("日には-2,500以上の値を入力して下さい");
		}
	}
	
	@Nested
	class 説明のチェック {
		@ParameterizedTest
        @ValueSource(strings = {"説明",
        		"3年後の27日前は何日か。ボーワッテゲダラ・ディサーナーヤカ・ムディヤンセーラーゲーさんと会える日。"})
    	void 正常系(String str) throws Exception{
        	testDateFormulaForm.setExplanation(str);
            // テスト実施
            validator.validate(testDateFormulaForm, bindingResult);
            // 結果検証
            assertThat(bindingResult.getFieldError()).isNull();
    	}
		
		@ParameterizedTest
        @ValueSource(strings = {"",
        		"3年後の27日前は何日か。ボーワッテゲダラ・ディサーナーヤカ・ムディヤンセーラーゲーさんと会える日だ。"})
		void 異常系_文字数不正(String str) throws Exception{
			testDateFormulaForm.setExplanation(str);
            // テスト実施
            validator.validate(testDateFormulaForm, bindingResult);
            // 結果検証
            assertThat(bindingResult.getFieldError().toString())
            	.contains("説明は50文字以下で入力して下さい");
		}
	}
}
