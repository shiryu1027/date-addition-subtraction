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
class UserFormTest{
	
	@Autowired
	Validator validator;
	
	private UserForm testUserForm = new UserForm();
    private BindingResult bindingResult = new BindException(testUserForm, "userForm");
	
    @Nested
    class メールアドレスのチェック {
    	
    	@BeforeEach
    	void setup() {
    		// 他のフィールドには正常値をセット
    		testUserForm.setPassword("Password04049");
    		testUserForm.setUsername("ユーザー");
    	}
    	
    	@ParameterizedTest
        @ValueSource(strings = {"user@gmail.com",
        		"abcdefghijklmnopqrstuvwxyz@gmail.com",
        		"fsaglasjglweFFAogo31314ewhowegfhoeghow@yahoo.co.jp"})
    	void 正常系(String str) throws Exception{
        	testUserForm.setMailAddress(str);
            // テスト実施
            validator.validate(testUserForm, bindingResult);
            // 結果検証
            assertThat(bindingResult.getFieldError()).isNull();
    	}
    	
    	@ParameterizedTest
        @ValueSource(strings = {"user1292194214124214214212142141232141221@gmail.com",
        		"abcdefghijklmnopqrstuvwxyz1234567890fff@ezweb.ne.jp",
        		"fsaglasjglweFFAogo31314ewhowegfhoeghowf@yahoo.co.jp"})
    	void 異常系_文字数不正(String str) throws Exception{
        	testUserForm.setMailAddress(str);
            // テスト実施
            validator.validate(testUserForm, bindingResult);
            // 結果検証
            assertThat(bindingResult.getFieldError().toString()).contains("50文字以下のメールアドレスを入力して下さい");
    	}
    }
    
    @Nested
    class パスワードのチェック {
    	@BeforeEach
    	void setup() {
    		// 他のフィールドには正常値をセット
    		testUserForm.setMailAddress("user@gmail.com");
    		testUserForm.setUsername("ユーザー");
    	}
    	
    	@ParameterizedTest
        @ValueSource(strings = {"password123Agrehrafs1Sfef",
        		"1kihiAks3v",
        		"gewee8ojofkew4A"})
    	void 正常系(String str) throws Exception{
        	testUserForm.setPassword(str);
            // テスト実施
            validator.validate(testUserForm, bindingResult);
            // 結果検証
            assertThat(bindingResult.getFieldError()).isNull();
    	}
    	
    	@ParameterizedTest
        @ValueSource(strings = {"pgu8JU3kf",
        		"gea8NJH8gihfl7keukn7fJi8rj",})
    	void 異常系_文字数不正(String str) throws Exception{
        	testUserForm.setPassword(str);
            // テスト実施
            validator.validate(testUserForm, bindingResult);
            // 結果検証
            assertThat(bindingResult.getFieldError().toString())
            	.contains("パスワードは10文字以上25文字以下、大小英文字(a-z、A-Z)、数字(0-9)の3種を必ず一回は使用して下さい");
    	}
    	
    	@ParameterizedTest
        @ValueSource(strings = {"password123agrehrafs1sfef",
        		"feegsdkihiAksv",
        		"AHRGSAG4325DGE",
        		"pgaasdgadfdsaf",
        		"32425454326624",
        		"BDFHHRHREHREERESDFEH"})
    	void 異常系_文字種不正(String str) throws Exception{
        	testUserForm.setPassword(str);
            // テスト実施
            validator.validate(testUserForm, bindingResult);
            // 結果検証
            assertThat(bindingResult.getFieldError().toString())
            	.contains("パスワードは10文字以上25文字以下、大小英文字(a-z、A-Z)、数字(0-9)の3種を必ず一回は使用して下さい");
    	}
    	
    	@ParameterizedTest
        @ValueSource(strings = {"psaokgniefiiwefewofnowenfe",
        		"3523grgert453ko9dsokodsfo9",
        		"AHRGSAGG4"})
    	void 異常系_文字数不正かつ文字種不正(String str) throws Exception{
        	testUserForm.setPassword(str);
            // テスト実施
            validator.validate(testUserForm, bindingResult);
            // 結果検証
            assertThat(bindingResult.getFieldError().toString())
            	.contains("パスワードは10文字以上25文字以下、大小英文字(a-z、A-Z)、数字(0-9)の3種を必ず一回は使用して下さい");
    	}	
    }
    
    @Nested
    class ユーザーネームのチェック {
    	
    	@BeforeEach
    	void setup() {
    		// 他のフィールドには正常値をセット
    		testUserForm.setMailAddress("user@gmail.com");
    		testUserForm.setPassword("Password04049");
    	}
    	
    	@ParameterizedTest
        @ValueSource(strings = {"k", "ユーザー123456"})
    	void 正常系(String str) throws Exception{
        	testUserForm.setUsername(str);
            // テスト実施
            validator.validate(testUserForm, bindingResult);
            // 結果検証
            assertThat(bindingResult.getFieldError()).isNull();
    	}
    	
    	@ParameterizedTest
        @ValueSource(strings = {"", "ユーザー1234567"})
    	void 異常系_文字数不正(String str) throws Exception{
        	testUserForm.setUsername(str);
            // テスト実施
            validator.validate(testUserForm, bindingResult);
            // 結果検証
            assertThat(bindingResult.getFieldError().toString())
            	.contains("10文字以下のユーザーネームを入力して下さい");
    	}	
    }
}
