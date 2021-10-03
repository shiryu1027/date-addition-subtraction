package com.example.demo.controller;

import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.security.Principal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoSession;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import com.example.demo.entity.DateFormula;
import com.example.demo.entity.Result;
import com.example.demo.form.BaseDateForm;
import com.example.demo.form.DateFormulaForm;
import com.example.demo.service.AutoDateFormulaCodeService;
import com.example.demo.service.CalcLogicService;
import com.example.demo.service.CalcService;
import com.example.demo.service.UserService;

@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser(username= "user@gmail.com", roles={"GENERAL"})
class CalcControllerTest {
	
	private MockitoSession session;
	private String mailAddress = "user@gmail.com";
	
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private CalcService calcService;
	
	@MockBean
	private UserService userService;
	
	@MockBean
	private CalcLogicService calcLogicService;
	
	@MockBean
	private ModelMapper modelMapper;
	
	@MockBean
	private AutoDateFormulaCodeService autoDateFormulaCodeService;
	
	@InjectMocks
	private CalcController sut;
	
	@BeforeEach
	void setup() {
		session = Mockito.mockitoSession().initMocks(this).startMocking();
	}
	
	@AfterEach
	void tearDown() throws Exception{
		session.finishMocking();
	}
	
	@Nested
	class 計算式データのリストと共にトップ画面を表示する {
		
		@Test
		void トップ画面を表示する() throws Exception{
			mockMvc.perform(get("/calc/"))
				.andExpect(status().isOk())
				.andExpect(view().name("calc/index"));
		}
		
		@Test
		void 計算式のデータを全件取得するメソッドを呼び出す( ) throws Exception {
			mockMvc.perform(get("/calc/"))
				.andExpect(status().isOk());
			verify(calcService, times(1)).getDateFormulas(mailAddress);
		}
		
		@Test
		void ユーザネームを取得するメソッドを呼び出す() throws Exception {
			mockMvc.perform(get("/calc/"))
				.andExpect(status().isOk());
			verify(userService, times(1)).getUsername(mailAddress);
		}
		
		@Test
		void モデルにユーザーネームと計算式のリストを流す() throws Exception {
			mockMvc.perform(get("/calc/"))
				.andExpect(status().isOk())
				.andExpect(model().attribute("username", userService.getUsername(mailAddress)))
				.andExpect(model().attribute("formulas", calcService.getDateFormulas(mailAddress)))
				.andExpect(model().hasNoErrors());
		}
	}
	
	@Nested
	class 基準日を渡したとき_計算結果とともにトップページを表示する {
		
		private BaseDateForm form;
		private List<DateFormula> formulas;
		private List<Result> results;
		
		@BeforeEach
		void setup() {
			form = new BaseDateForm();
			form.setBaseDate(LocalDate.of(1997, 10, 27));
			
			formulas = new ArrayList<>();
			DateFormula dateFormula = new DateFormula();
			formulas.add(dateFormula);
			
			doReturn(formulas).when(calcService).getDateFormulas(mailAddress);
			
			results = new ArrayList<>();
			doReturn(results).when(calcLogicService).getCalcResults(formulas, form.getBaseDate());
		}
		
		@Test
		void トップ画面を表示する() throws Exception{
			mockMvc.perform(post("/calc/result")/*.flashAttr("baseDateForm", new BaseDateForm())*/.with(csrf()))
				.andExpect(status().isOk())
				.andExpect(view().name("calc/index"));
		}
		
		@Test
		void モデルにユーザーネームと計算結果と基準日を流す() throws Exception {
			mockMvc.perform(post("/calc/result").flashAttr("baseDateForm", form).with(csrf()))
				.andExpect(status().isOk())
				.andExpect(model().attribute("username", userService.getUsername(mailAddress)))
				.andExpect(model().attribute("results", calcLogicService.getCalcResults(formulas, form.getBaseDate())))
				.andExpect(model().attribute("baseDate", form.getBaseDate()))
				.andExpect(model().hasNoErrors());
		}
		
		@Test
		void ユーザネームを取得するメソッドを呼び出す() throws Exception {
			mockMvc.perform(post("/calc/result").flashAttr("baseDateForm", form).with(csrf()))
				.andExpect(status().isOk());
			verify(userService, times(1)).getUsername(mailAddress);
		}
		
		@Test
		void 計算式のデータを全件取得するメソッドを呼び出す( ) throws Exception {
			mockMvc.perform(post("/calc/result").flashAttr("baseDateForm", form).with(csrf()))
				.andExpect(status().isOk());
			verify(calcService, times(1)).getDateFormulas(mailAddress);
		}
		
		@Test
		void 計算式と基準日をもとに計算結果を算出するメソッドを呼び出す( ) throws Exception {
			mockMvc.perform(post("/calc/result").flashAttr("baseDateForm", form).with(csrf()))
				.andExpect(status().isOk());
			verify(calcLogicService, times(1)).getCalcResults(formulas, form.getBaseDate());
		}
		
		@Test
		void 基準日にエラーがあったとき_トップ画面を表示する( ) throws Exception {
			mockMvc.perform(post("/calc/result").flashAttr("baseDateForm", new BaseDateForm()).with(csrf()))
				.andExpect(status().isOk())
				.andExpect(view().name("calc/index"))
				.andExpect(model().hasErrors());
		}
	}
	
	@Nested
	class 計算式の新規登録画面を表示する {
		
		@Test
		void 新規登録画面を表示する() throws Exception {
			mockMvc.perform(get("/calc/add"))
				.andExpect(status().isOk())
			.andExpect(view().name("calc/add"));
		}
		
		@Test
		void ユーザネームを取得するメソッドを呼び出す() throws Exception {
			mockMvc.perform(get("/calc/add"))
				.andExpect(status().isOk());
			verify(userService, times(1)).getUsername(mailAddress);
		}
		
		@Test
		void モデルにユーザーネームと計算式のリストを流す() throws Exception {
			mockMvc.perform(get("/calc/add"))
				.andExpect(status().isOk())
				.andExpect(model().attribute("username", userService.getUsername(mailAddress)))
				.andExpect(model().hasNoErrors());
		}
	}
	
	@Nested
	class 計算式をDBに新規登録して_トップ画面にリダイレクトする {
		
		private DateFormulaForm form;
		private DateFormula dateFormula;
		private Principal principal;
		
		@BeforeEach
		void setup() {
			form = new DateFormulaForm();
			form.setYear(1);
			form.setMonth(0);
			form.setDay(0);
			form.setExplanation("一年後");
			
			dateFormula = new DateFormula();
			dateFormula.setYear(1);
			dateFormula.setMonth(0);
			dateFormula.setDay(0);
			dateFormula.setExplanation("一年後");
			doReturn(dateFormula).when(modelMapper).map(form, DateFormula.class);
			
			dateFormula.setDateFormulaCode("+1Y");
			doReturn(dateFormula).when(autoDateFormulaCodeService).autoDateFormulaCode(dateFormula);
			
			GrantedAuthority authority = new SimpleGrantedAuthority("ROLE_GENERAL");
			Collection<GrantedAuthority> authorities = new ArrayList<>();
			authorities.add(authority);
			UserDetails userDetails = new User("user@gmail.com", "password", authorities);
			
			principal = new UsernamePasswordAuthenticationToken(
					userDetails, "password", authorities);
			
			doNothing().when(calcService).addDateFormula(dateFormula, principal);
		}
		
		@Test
		void 計算式の新規登録後_リダイレクトしトップ画面を表示する() throws Exception {
			mockMvc.perform(post("/calc/add").flashAttr("dateFormulaForm", form).with(csrf()))
				.andExpect(status().isFound())
				.andExpect(redirectedUrl("/calc/"));
		}
		
		@Test
		void FormをEntityに変換するメソッドが呼び出されている() throws Exception {
			mockMvc.perform(post("/calc/add").flashAttr("dateFormulaForm", form).with(csrf()))
				.andExpect(status().isFound());
			verify(modelMapper, times(1)).map(form, DateFormula.class);
		}
		
		@Test
		void 計算式コードを自動生成するメソッドが呼び出されている() throws Exception {
			mockMvc.perform(post("/calc/add").flashAttr("dateFormulaForm", form).with(csrf()))
				.andExpect(status().isFound());
			verify(autoDateFormulaCodeService, times(1)).autoDateFormulaCode(dateFormula);
		}
		
		@Test
		void 計算式をDBに登録するメソッドが呼び出されている() throws Exception {
			mockMvc.perform(post("/calc/add").flashAttr("dateFormulaForm", form).with(csrf()))
				.andExpect(status().isFound());
			verify(calcService, times(1)).addDateFormula(dateFormula, principal);
		}
		
		@Test
		void 登録しようとする計算式にエラーがあれば_計算式の新規登録画面を表示する() throws Exception {
			DateFormulaForm errorForm = new DateFormulaForm();
			errorForm.setYear(1000000000);
			errorForm.setMonth(0);
			errorForm.setDay(0);
			errorForm.setExplanation("一年後");
					
			mockMvc.perform(post("/calc/add").flashAttr("dateFormulaForm", errorForm).with(csrf()))
			.andExpect(status().isOk());
		}
	}
	
	@Nested
	class 計算式を更新する画面を表示する {
		
		private DateFormula dateFormula;
		
		@BeforeEach
		void setup() {
			dateFormula = new DateFormula();
			dateFormula.setDateFormulaId(1);
			dateFormula.setDateFormulaCode("+1Y");
			dateFormula.setYear(1);
			dateFormula.setMonth(0);
			dateFormula.setDay(0);
			dateFormula.setExplanation("一年後");
			dateFormula.setUserId(1);
			doReturn(dateFormula).when(calcService).getDateFormula(1);
		}
		
		@Test
		void 計算式更新画面を表示する() throws Exception {
			mockMvc.perform(get("/calc/alter/id={id}", "1"))
				.andExpect(status().isOk())
				.andExpect(view().name("calc/alter"));
		}
		
		@Test
		void ユーザネームを取得するメソッドを呼び出す() throws Exception {
			mockMvc.perform(get("/calc/alter/id={id}", "1"))
				.andExpect(status().isOk());
			verify(userService, times(1)).getUsername(mailAddress);
		}
		
		@Test
		void 選択した計算式を取得するメソッドを呼び出す() throws Exception {
			mockMvc.perform(get("/calc/alter/id={id}", "1"))
				.andExpect(status().isOk());
			verify(calcService, times(1)).getDateFormula(1);
		}
		
		@Test
		void モデルにユーザーネームと選択した計算式を流す() throws Exception {
			mockMvc.perform(get("/calc/alter/id={id}", "1"))
				.andExpect(status().isOk())
				.andExpect(model().attribute("username", userService.getUsername(mailAddress)))
				.andExpect(model().attribute("dateFormula", calcService.getDateFormula(1)))
				.andExpect(model().hasNoErrors());
		}
	}
	
	@Nested
	class 計算式を更新し_トップ画面にリダイレクトする {
		
		private DateFormulaForm form;
		private DateFormula dateFormula;
		private Principal principal;
		
		@BeforeEach
		void setup() {
			form = new DateFormulaForm();
			form.setDateFormulaId(1);
			form.setYear(1);
			form.setMonth(0);
			form.setDay(0);
			form.setExplanation("一年後");
			
			dateFormula = new DateFormula();
			dateFormula.setDateFormulaId(1);
			dateFormula.setYear(1);
			dateFormula.setMonth(0);
			dateFormula.setDay(0);
			dateFormula.setExplanation("一年後");
			doReturn(dateFormula).when(calcService).getDateFormula(1);
			doReturn(dateFormula).when(modelMapper).map(form, DateFormula.class);
			
			GrantedAuthority authority = new SimpleGrantedAuthority("ROLE_GENERAL");
			Collection<GrantedAuthority> authorities = new ArrayList<>();
			authorities.add(authority);
			UserDetails userDetails = new User("user@gmail.com", "password", authorities);
			
			principal = new UsernamePasswordAuthenticationToken(
					userDetails, "password", authorities);
			
			doNothing().when(calcService).addDateFormula(dateFormula, principal);
		}
		
		@Test
		void 計算式の新規登録後_リダイレクトしトップ画面を表示する() throws Exception {
			mockMvc.perform(post("/calc/alter/id={id}/post", "1").flashAttr("dateFormulaForm", form).with(csrf()))
				.andExpect(status().isFound())
				.andExpect(redirectedUrl("/calc/"));
		}
		
		@Test
		void FormをEntityに変換するメソッドが呼び出されている() throws Exception {
			mockMvc.perform(post("/calc/alter/id={id}/post", "1").flashAttr("dateFormulaForm", form).with(csrf()))
				.andExpect(status().isFound());
			verify(modelMapper, times(1)).map(form, DateFormula.class);
		}
		
		@Test
		void 計算式コードを自動生成するメソッドが呼び出されている() throws Exception {
			mockMvc.perform(post("/calc/alter/id={id}/post", "1").flashAttr("dateFormulaForm", form).with(csrf()))
				.andExpect(status().isFound());
			verify(autoDateFormulaCodeService, times(1)).autoDateFormulaCode(dateFormula);
		}
		
		@Test
		void 計算式を更新するメソッドが呼び出されている() throws Exception {
			mockMvc.perform(post("/calc/alter/id={id}/post", "1").flashAttr("dateFormulaForm", form).with(csrf()))
				.andExpect(status().isFound());
			verify(calcService, times(1)).alterDateFormula(dateFormula, principal);
		}
		
		@Test
		void 更新しようとする計算式にエラーがあれば_計算式の更新画面を表示する() throws Exception {
			DateFormulaForm errorForm = new DateFormulaForm();
			errorForm.setDateFormulaId(1);
			errorForm.setYear(1000000000);
			errorForm.setMonth(0);
			errorForm.setDay(0);
			errorForm.setExplanation("一年後");
					
			mockMvc.perform(post("/calc/alter/id={id}/post", "1").flashAttr("dateFormulaForm", errorForm).with(csrf()))
			.andExpect(status().isOk())
			.andExpect(view().name("calc/alter"));
		}
	}
	
	@Nested
	class 計算式を削除し_トップ画面にリダイレクトする {
		
		@Test
		void 計算式の削除後_リダイレクトしトップ画面を表示する() throws Exception {
			mockMvc.perform(post("/calc/delete/id={id}", "1").with(csrf()))
				.andExpect(status().isFound())
				.andExpect(redirectedUrl("/calc/"));
		}
		
		@Test
		void 計算式を削除するメソッドが呼び出されている() throws Exception {
			mockMvc.perform(post("/calc/delete/id={id}", "1").with(csrf()))
				.andExpect(status().isFound());
			verify(calcService, times(1)).deleteDateFormula(1);
		}
	}
}
