package com.example.demo.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoSession;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.demo.entity.AppUser;
import com.example.demo.entity.DateFormula;
import com.example.demo.form.DateFormulaForm;
import com.example.demo.mapper.AdditionSubtractionMapper;

@SpringBootTest
@DisplayName("CalcServiceのテスト")
class CalcServiceTest {
	
	private MockitoSession session;
	
	@Mock
	private AdditionSubtractionMapper mapper;
	
	@Mock
	private UserService userService;
	
	@Mock
	private Principal principal;
	
	@InjectMocks
	private CalcService target;
	
	@BeforeEach
	void setup() {
		session = Mockito.mockitoSession().initMocks(this).startMocking();
	}
	
	@AfterEach
	void tearDown() {
	    session.finishMocking();
	}
	
	@Nested
	class 加減算用データを1件取得する {
		
		int id;
		DateFormula actual;
		
		@BeforeEach
		void setup() {
			id = 1;
			DateFormula dateFormula = new DateFormula(id, "+3Y-2M+1D", 3, -2, 1, "3年後2か月前一日後", 1);;
			doReturn(dateFormula).when(mapper).calcDataOne(1);
			actual = target.getDateFormula(id);
		}
		
		@Test
		void mapperクラスのcalcDataOneメソッドを一回呼び出す() throws Exception {
			verify(mapper, times(1)).calcDataOne(id);
		}
		
		@Test
		void 戻り値としてCalcData型のデータを返す() throws Exception {
			assertThat(actual).isEqualTo(mapper.calcDataOne(1));
		}
	}
	
	@Nested
	class ユーザーが持つ加減算用データを全件取得 {
		
		int userId;
		String mailAddress;
		List<DateFormula> actual;
		
		@BeforeEach
		void setup() {
			// userId=1のメールアドレスがuser@co.jpだと仮定
			userId = 1;
			mailAddress = "user@co.jp";
			List<DateFormula> list = new ArrayList<DateFormula>();
			DateFormula calcData1 = new DateFormula(1, "+3Y-2M+1D", 3, -2, 1, "3年後2か月前1日後", userId);
			DateFormula calcData2 = new DateFormula(2, "+6Y-11M+10D", 6, -11, 10, "6年後11か月前10日後", userId);
			DateFormula calcData3 = new DateFormula(3, "-2Y+2M-1D", -2, 2, -1, "2年前2か月後1日前", userId);
			list.add(calcData1);
			list.add(calcData2);
			list.add(calcData3);
			doReturn(list).when(mapper).calcDataAll(mailAddress);
			actual = target.getFormulas(mailAddress);
		}
		
		@Test
		void mapperクラスのcalcDataAllメソッドを一回呼び出す() {
			verify(mapper, times(1)).calcDataAll(mailAddress);
		}
		
		@Test
		void 戻り値としてCalcData型のデータを返す() throws Exception {
			assertThat(actual).isEqualTo(mapper.calcDataAll(mailAddress));
		}
	}
	
	@Nested
	class 加減算データを一件新規登録する {
		
		int userId;
		String mailAddress;
		AppUser user;
		DateFormula form;
		
		@BeforeEach
		void setup() {
			userId = 1;
			mailAddress = "user.co,jp";
			user = new AppUser(userId, mailAddress, "ユーザー", "password", "ROLE_GENERAL");
			form = new DateFormula(1, "+3Y-2M+1D", 3, -2, 1, "3年後2か月前1日後", userId);
			doReturn(user).when(userService).getSignInUser(mailAddress);
			doReturn(mailAddress).when(principal).getName();
			target.addDateFormula(form, principal);
		}
		
		@Test
		void UsersServiceクラスのgetSignInUserメソッドを一回呼び出す() throws Exception{
			verify(userService, times(1)).getSignInUser(principal.getName());
		}
		
		@Test
		void UsersServiceクラスのgetSignInUserメソッドによってAppUser型のデータを返す() throws Exception{
			assertThat(user).isEqualTo(userService.getSignInUser(principal.getName()));
		}
		
		@Test
		void appUserから持ってきたuserIdとformのuserIdが同値() throws Exception{
			assertThat(form.getUserId()).isEqualTo(user.getUserId());
		}
		
		@Test
		void mapperクラスのcalcDataInsertメソッドを一回呼び出す() {
			verify(mapper, times(1)).calcDataInsert(form);
		}
		
	}
	
	@Nested
	class 既存加減算用データの更新 {
		
		int userId;
		String mailAddress;
		AppUser user;
		DateFormula form;
		
		@BeforeEach
		void setup() {
			userId = 1;
			mailAddress = "user.co,jp";
			user = new AppUser(userId, mailAddress, "ユーザー", "password", "ROLE_GENERAL");
			form = new DateFormula(1, "+3Y-2M+1D", 3, -2, 1, "3年後2か月前1日後", userId);
			doReturn(user).when(userService).getSignInUser(mailAddress);
			doReturn(mailAddress).when(principal).getName();
			target.alterDateFormula(form, principal);
		}
		
		@Test
		void UsersServiceクラスのgetSignInUserメソッドを一回呼び出す() throws Exception{
			verify(userService, times(1)).getSignInUser(principal.getName());
		}
		
		@Test
		void UsersServiceクラスのgetSignInUserメソッドによってAppUser型のデータを返す() throws Exception{
			assertThat(user).isEqualTo(userService.getSignInUser(principal.getName()));
		}
		
		@Test
		void mapperクラスのcalcDataInsertメソッドを一回呼び出す() {
			verify(mapper, times(1)).calcDataUpdate(form);
		}
	}
	
	@Nested
	class 既存加減算用データの削除 {
		
		int id;
		
		@BeforeEach
		void setup() {
			id = 1;
			doNothing().when(mapper).calcDataDelete(id);
			target.deleteDateFormula(id);
		}
		
		@Test
		void mapperクラスのcalcDataOneメソッドを一回呼び出す() throws Exception {
			verify(mapper, times(1)).calcDataDelete(id);
		}
	}
}
