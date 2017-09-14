/*******************************************************************************
 * Copyright (C) 2017 http://bndy.net
 * Created by Bendy (Bing Zhang)
 ******************************************************************************/
package net.bndy.wf;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import net.bndy.wf.modules.app.services.UserDetailsServiceImpl;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	DataSource dataSource;

	@Autowired
	private UserDetailsServiceImpl userDetailsService;
	
	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers(HttpMethod.OPTIONS, "/**");
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// TODO: remove before release
		http.csrf().disable();
		
		http.authorizeRequests()
			.antMatchers("/webjars/**", "/static/**", "/api/**", "/test/**", "/v2/api-docs",
						"/docs/*/**", "/error",
						"/*",
						"/sso/login*", "/sso/signup").permitAll()
			.anyRequest().authenticated();

		http
			.formLogin()
				.loginPage("/sso/login")
				.successHandler(new AuthSuccessHandler())
				.permitAll()
				.and()
			.logout()
				.logoutUrl("/sso/logout")
				.logoutSuccessUrl("/sso/login?logout")
				.deleteCookies("JSESSIONID")
				.permitAll();
	}

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		// Solution 1
		// auth.inMemoryAuthentication()
		// .withUser("admin").password("123456").roles("ADMIN");

		// Solution 2
		// auth.jdbcAuthentication().dataSource(this.dataSource)
		// .usersByUsernameQuery("SELECT username, password, enabled FROM
		// app_user WHERE username=?")
		// .authoritiesByUsernameQuery("SELECT username, role FROM app_user_role
		// WHERE username=?");

		// Solution 3
		auth.userDetailsService(this.userDetailsService).passwordEncoder(this.bCryptPasswordEncoder());
	}
}
