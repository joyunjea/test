package com.test.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

import com.test.service.AccountService;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	AccountService accountService;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// TODO Auto-generated method stub
		
		//DB���� �о�ͼ� �����ϰų� , �ҽ��� ������ ��θ� �Ʒ��� ���� �������� �ڵ��س��� �ᵵ ������� 
		String permitAllUrl [] = {"/","/info","/account/**","/login**","logoutForm"};
		String adminUrl [] = {"/admin"};
		String userUrl [] = {"/member"};
		String loginUrl = "/login";
		String logoutUrl = "/logout";
		
		
		http.csrf().disable()
		.authorizeRequests() //HttpServletRequest �� ����Ͽ� �������� ���� ���� 
		.mvcMatchers(permitAllUrl).permitAll() //��� ������ ����ϴ� Url 
		.mvcMatchers(adminUrl).hasRole("ADMIN") // "/admin" ��û��  "ADMIN ������ ������ �־�� ����Ѵ�.
		.mvcMatchers(userUrl).hasRole("MEMBER")
		.anyRequest().authenticated(); // �� ���� ��û�� ������ �ֱ⸸ �ϸ� ����Ѵ�.
		
		//������ ������ �α����϶�� ƨ�ܳ��� URL
		http.formLogin()
		.loginPage(loginUrl)
		.permitAll();
		
		//�α׾ƿ������ϸ� ���� Url
		http.logout()
		.logoutUrl(logoutUrl)
		.logoutSuccessUrl(loginUrl)
		.invalidateHttpSession(true).deleteCookies("JSESSIONID");
		
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		// TODO Auto-generated method stub
		auth.userDetailsService(accountService);
	}
	
	 @Bean(name = "mvcHandlerMappingIntrospector")
	    public HandlerMappingIntrospector mvcHandlerMappingIntrospector() {
	        return new HandlerMappingIntrospector();
	    }
	
	
}
