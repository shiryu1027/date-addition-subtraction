package com.example.demo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.example.demo.service.impl.UserDetailsServiceImpl;

/* セキュリティ情報設定ページ */
@EnableWebSecurity
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	/* 解体新書では、UserDetailsServiceであったが、UserDetailsServiceImplを使うはずなので、以下が正しいと思われる */
	@Autowired
	private UserDetailsServiceImpl userDetailsService;
	
	/* パスワードの暗号化 */
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	/* セキュリティの対象外を設定 */
	@Override
	public void configure(WebSecurity web) throws Exception {
		
		/* セキュリティを適用しないファイルを指定 */
		web
			.ignoring()
				.antMatchers("/css/**")
				.antMatchers("/image/**");
   				
	}
	
	/* セキュリティの各種設定 */
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		/* サインイン不要ページの設定 */
		http
			.authorizeRequests()
				.antMatchers("/user/signin").permitAll() // 直リンクOK
				.antMatchers("/user/signup").permitAll() 
				//.antMatchers("/admin").hasAuthority("ROLE_ADMIN") // 権限制御
				.anyRequest().authenticated(); // それ以外は直リンクNG
		
		/* サインイン処理 */
		http
			.formLogin()
				.loginProcessingUrl("/user/signin") // サインイン処理のパス
				.loginPage("/user/signin") // サインインページの指定
				.failureUrl("/user/signin?error") // サインイン失敗時の遷移先
				.usernameParameter("mailAddress") // サインインページのユーザーID
				.passwordParameter("password") // サインインページのパスワード
				.defaultSuccessUrl("/calc/", true); // 成功後の遷移先
		
		/* サインアウト処理 */
		http
			.logout()
				.logoutRequestMatcher(new AntPathRequestMatcher("/user/signout")) // GETメソッドでリクエストを送りたいときに付ける(DEFAULTはPOST)
				.logoutUrl("/user/signout") // ログアウトのリクエスト先パス
				.logoutSuccessUrl("/user/signin?signout"); // ログアウト成功時の遷移先
		
		/* サインアウトのコントローラーはSpringセキュリティがやってくれるため不要 */
		
		/* CSRF対策を無効に設定(基本は使わない) */
		//http.csrf().disable();
	}
	
	/* 認証の設定 */
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		
		PasswordEncoder encoder = passwordEncoder();
		
		/* インメモリ認証(後にコメントアウト)*/
		/*auth
			.inMemoryAuthentication()
				.withUser("user")
					.password(encoder.encode("user"))
					.roles("GENERAL")
				.and()
				.withUser("admin")
					.password(encoder.encode("admin"))
					.roles("ADMIN");*/
		
		/* ユーザーデータで認証 */
		auth
			.userDetailsService(userDetailsService) // 引数に自作UserDetailsServiceを指定
			.passwordEncoder(encoder); // 必須
		
	}
	
}
