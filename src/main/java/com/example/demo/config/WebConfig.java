package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.example.demo.interceptor.HeaderInterceptor;

@Configuration
public class WebConfig implements WebMvcConfigurer{  // Register our interceptors
	public final HeaderInterceptor headerInterceptor;
	
	public WebConfig(HeaderInterceptor headerInterceptor) {
		this.headerInterceptor = headerInterceptor;
	}
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(headerInterceptor)
		.addPathPatterns("/**");
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
