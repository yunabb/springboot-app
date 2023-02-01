package com.example.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * spring security 설정정보를 제공하는 클래스다.<br />
 *
 */
@EnableWebSecurity
public class CustomSecurityConfig extends WebSecurityConfigurerAdapter {

	// 인증에 필요한 사용자정보와 권한정보를 포함하는 UserDetails객체를 반환하는 CustomUserDetailsService객체를 의존성 주입받는다.
	@Autowired
	private CustomUserDetailsService customUserDetailsService;
	// 회원가입시 비밀번호 암호화에 사용했던 비밀번호인코더 객체를 의존성 주입받는다.
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	/*
	 * HTTP 요청에 대한 인증/인가 관련 설정정보를 정의한다.<br />
	 * 1. HTTP 요청에 대한 인가정책(권한체크 정책)을 설정한다.
	 * 2. 인증 정책(로그인 정책)을 설정한다.
	 * 3. 로그아웃정책을 설정한다.
	 * 
	 * HttpSecurity
	 * 	- 각종 보안관련 설정을 담당하는 객체
	 * 	- HttpSecurity는 각종 xxxConfigurer객체를 반환하는 메소드를 제공한다.
	 * 		csrf() 메소드는 csrf 토큰 사용여부를 설정하는 CsrfConfigurer를 반환한다.
	 * 		authorizeHttpRequests() 메소드는 인가정책을 설정하는 AuthorizationManagerRequestMatcherRegistry를 반환한다.
	 * 		formLogin() 메소드는 폼로그인 정책을 설정하는 FormLoginConfigurer를 반환한다.
	 * 		logout() 메소드는 로그아웃 정책을 설정하는 LogoutConfigurer를 반환한다.
	 * 		exceptionHandling() 메소드는 예외처리 정책을 설정하는 ExceptionHandlingConfigurer를 반환한다.
	 * 		xxxConfigurer객체의 and() 메소드는 HttpSecurity객체를 반환한다.
	 */
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			// CSRF 토큰 사용여부 설정
			//.csrf()		// CSRF(사이트 간 요청위조) 방지를 위한 csrf토큰 사용여부 설정, CsrfConfigurer 객체를 반환한다.
			//.disable()	// csrf 토큰 사용을 비활성화한다.(기본값은 csrf 토큰을 사용한다.)
			
			// 1. 인가정책 설정 시작
			.authorizeHttpRequests()	// 모든 요청에 대해서 아래에 설정된 인가정책을 적용하도록 한다. AuthorizationManagerRequestMatcherRegistry 객체를 반환한다.
			.antMatchers("/", "/register", "/registered", "/login").permitAll()	// 접근권한 보유 여부를 확인하지 않고 접근을 허용한다.
			.antMatchers("/post/**").hasAnyRole("GUEST", "USER")	// 제시된 요청은 ROLE_GUEST 혹은 ROLE_USER 권한이 필요하다. 
			.antMatchers("/user/**").hasRole("USER")				// 제시된 요청은 ROLE_USER 권한이 필요하다.
			.antMatchers("/admin/**").hasRole("ADMIN")				// 제시된 요청은 ROLE_ADMIN 권한이 필요하다.
			.anyRequest().authenticated()							// 위에서 제시된 요청 외의 모든 요청도 반드시 인증인 필요하다.
			// 1. 인가정책 설정 종료
		.and()	// HttpSecurity 객체를 반환한다. 새 보안설정에 필요한 객체(xxxConfigurer)를 반환하는 메소드를 호출해서 보안설정을 추가한다.
			// 2. 인증정책 설정 시작
			.formLogin()						// 인증방식은 폼인증 방식을 사용하도록 지정한다. FormLoginConfigurer객체를 반환한다.
			.loginPage("/login")				// 로그인 폼을 요청하는 URI을 지정한다.
			.loginProcessingUrl("/login")		// 로그인 처리를 요청하는 URI를 지정한다.
			.usernameParameter("id")			// 로그인 폼의 사용자이름 입력필드 이름을 지정한다.
			.passwordParameter("password")		// 로그인 폼의 비밀번호 입력필드 이름을 지정한다.
			.defaultSuccessUrl("/")				// 로그인 성공시 리다이렉션할 URI를 지정한다.
			.failureUrl("/login?error=fail")	// 로그인 실패했을 경우 재요청할 URI를 지정한다.
			// 2. 인증정책 설정 종료
		.and()
			// 3. 로그아웃정책 설정 시작
			.logout()							// 로그아웃, LogoutConfigurer객체를 반환한다.
			.logoutUrl("/logout")				// 로그아웃 처리를 요청하는 URI를 지정한다.
			.logoutSuccessUrl("/")				// 로그아웃 성공시 리다이렉션할 URI를 지정한다.
			// 3. 로그아웃정책 설정 종료
		.and()
			// 4. 예외처리 정책 설정 시작
			.exceptionHandling()				// 예외처리, ExceptionHandlingConfigurer객체를 반환한다.
			.accessDeniedPage("/access-denied");// 접근이 거부되었을 때 요청할 URI를 지정한다.
			// 4. 예외처리 정책 설정 종료
	}
	
	// 이미지, 스타일시트, 자바스크립트소스와 같은 정적 컨텐츠는 인증/인가 작업을 수행하지 않도록 설정한다.
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/resources/**", "/favicon.ico");
	}
	
	// 사용자정의 UserDetailsService객체와 이 애플리케이션에서 사용하는 비밀번호 인코더를 AuthenticationManager에 등록시킨다.
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(customUserDetailsService)
			.passwordEncoder(passwordEncoder);
	}
	
}
