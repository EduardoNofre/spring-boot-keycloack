package com.spring.boot.keycloak.app.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.session.RegisterSessionAuthenticationStrategy;
import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy;

import com.spring.boot.keycloak.app.handle.SecurityHandler;

import lombok.extern.slf4j.Slf4j;


@Slf4j
@Configuration
public class SecurityConfig {
	
	@Value("${keycloak.auth-server-url}")
	private String urlKeycloak;
	
	@Value("${keycloak.recurso.role}")
	private String role_sistema;
	
	@Autowired
	private SecurityEntryPoint entryPoint;
	
	@Autowired
	private SecurityHandler accessDeniedHandler;
	
	@Autowired
	private SecurityUserDetails userDetails;
	
	
	
	@Bean
	protected SessionAuthenticationStrategy sessionAuthenticationStrategy() {
		return new RegisterSessionAuthenticationStrategy(new SessionRegistryImpl());
	}
	
    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        log.info("[SecurityConfig - iniciado]");
        http.cors(cors -> cors.disable())
            .csrf(csrf -> csrf.disable())
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .exceptionHandling(exception -> 
                exception.authenticationEntryPoint(entryPoint)
                        .accessDeniedHandler(accessDeniedHandler)
            )
            .userDetailsService(userDetails.loginService())
            .authorizeHttpRequests(auth -> 
                auth.requestMatchers(getUrlIgnoradas()).permitAll()// URLs permitidas sem autenticacao
                    .anyRequest().hasRole(role_sistema) // Outras URLs exigem autenticacao
            )
            .httpBasic(Customizer.withDefaults());

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
	            "/health-check"
	        }; 
	    }
}
