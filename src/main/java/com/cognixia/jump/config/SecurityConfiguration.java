package com.cognixia.jump.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import com.cognixia.jump.service.MyUserDetailsService;

@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Autowired
	MyUserDetailsService userDetailsService;

	//configuration for the authentication (who are you?)
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService);
		/*
		auth.inMemoryAuthentication()
		.withUser("user1")
		.password( passwordEncoder().encode("123") )
		.roles("USER");
		*/
	}

	/*public PasswordEncoder passwordEncoder() {
		return NoOpPasswordEncoder.getInstance();
		//return PasswordEncoderFactories.createDelegatingPasswordEncoder();
	}*/

	@Override
	public void configure(HttpSecurity http) throws Exception {
//		http.csrf().disable()
		http.authorizeRequests()
//		.antMatchers("/api/user").hasRole("USER")
//		.antMatchers("/api/**").hasRole("USER")
		.anyRequest().authenticated()//.and().authorizeRequests()
//		.antMatchers("/api/useraccess").hasAnyRole("USER")
//		.antMatchers(HttpMethod.GET, "/api/todo").hasRole("USER")
		.and().httpBasic();

	}
}
