package com.example.demo.config;

import org.springframework.security.config.Customizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
public class WebSecurityConfig {
	
	private final PasswordEncoder passwordEncoder;
	
	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {

	    httpSecurity
	        .authorizeHttpRequests(auth -> auth
	            .requestMatchers("/employees/**").authenticated()   // /employees are authenticateed
	            .anyRequest().permitAll()) 							// rest are not 	
	        
	        .csrf(csrfConfig -> csrfConfig
	        		.disable())					 // Disable CSRF, not recommended in production
	        
//	        .sessionManagement(sessionConfig -> sessionConfig
//	        		.sessionCreationPolicy(SessionCreationPolicy.STATELESS))   Disable JSESSION , not recommended in production
	       
	        .formLogin(Customizer.withDefaults()); // Enable Login Form
	        
//	        .logout(Customizer.withDefaults());   Enable Auto-Logout
	    
	    return httpSecurity.build();
	}
		
	// Custom Authentication Providers
	@Bean
	UserDetailsService userDetailsService() {
		UserDetails user1 = User.withUsername("admin")
				.password(passwordEncoder.encode("password"))
				.build(); 
		
		return new InMemoryUserDetailsManager(user1);
	}
}
