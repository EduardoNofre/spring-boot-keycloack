package com.spring.boot.keycloack.app.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

		http.csrf(AbstractHttpConfigurer::disable).authorizeHttpRequests(auth -> {
			auth.
			requestMatchers(this.getUrlIgnoradas()).permitAll();
		});

		return http.build();
	}
	
	
	  protected String[] getUrlIgnoradas() {
	        return new String[] { 
	            "/health-check", 
	            "/actuator/**", 
	            "/swagger-ui/**", 
	            "/v3/**", 
	            "/h2-console/**", 
	            "/swagger-ui.css", 
	            "/swagger-ui-standalone-preset.js", 
	            "/swagger-ui-bundle.js", 
	            "/favicon.ico",
	            "*/login/**" 
	        }; 
	    }
}
