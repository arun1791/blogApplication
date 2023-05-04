package com.blog.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.blog.security.JwtAuthenticationEntryPoint;
import com.blog.security.JwtAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig  {
	@Autowired
	private CustomeUserDetailsService customeUserDetailsService;
	@Autowired
	private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
	@Autowired
	private JwtAuthenticationFilter jwtAuthenticationFilter;
	
	@Bean
	public PasswordEncoder passwordEncoder()
	{
		return new BCryptPasswordEncoder();
		
	}
	
//	@Bean
//	public UserDetailsService userDetailsService()
//	{
////		UserDetails normalUser=User.withUsername("arun")
////				.password(passwordEncoder().encode("password"))
////				.roles("NORMAL")
////				.build();
////		
////		InMemoryUserDetailsManager inMemoryUserDetailsManager = new InMemoryUserDetailsManager(normalUser);
////		return inMemoryUserDetailsManager;
//		
//		return new CustomeUserDetailsService();
//		
//		
//	}
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception
	{
		httpSecurity.csrf().disable()
					.authorizeHttpRequests()
					.requestMatchers("/api/v1/auth/**")
					.permitAll()
					.anyRequest()
					.authenticated()
					.and()
					.exceptionHandling()
					.authenticationEntryPoint(this.jwtAuthenticationEntryPoint)
					.and()
					.sessionManagement()
					.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		
		httpSecurity.addFilterBefore(this.jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
		
		
		return httpSecurity.build();
		
	}
	
	
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		
		auth.userDetailsService(this.customeUserDetailsService).passwordEncoder(passwordEncoder());
	}
//	@Override
//	@Bean
//	public AuthenticationManager authenticationManagerBean() throws Exception {
//	    return super.authenticationManagerBean();
//	}
	
	@Bean
	  AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {

	    return authenticationConfiguration.getAuthenticationManager();
	  }
 
}
