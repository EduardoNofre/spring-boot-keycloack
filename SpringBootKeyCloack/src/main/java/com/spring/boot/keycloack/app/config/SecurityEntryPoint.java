package com.spring.boot.keycloack.app.config;

import java.io.IOException;
import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class SecurityEntryPoint implements AuthenticationEntryPoint{

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException authenticationException) throws IOException, ServletException {
		
		String msg = authenticationException.getMessage();
    	if (authenticationException instanceof InsufficientAuthenticationException) {
    		msg = "Autênticação é obrigatória";
    		
    	}
    	AccessDeniedException accessDeniedException = new AccessDeniedException(msg);
    	montaExceptionJson(HttpStatus.UNAUTHORIZED, response,accessDeniedException);
		
	}

	
	private void montaExceptionJson(HttpStatus status, HttpServletResponse response, Exception e)
			throws IOException {
		response.addHeader("KEYCLOAK", e.getMessage());
		response.setStatus(status.value());
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		String msgJson = String.format("{\"error\": %s,\"message\": \"%s\",\"path\": \"%s\",\"status\": \"%s\",\"timestamp\": \"%s\"}",
				status.getReasonPhrase(), e.getMessage(),"",status.value(),LocalDateTime.now().toString());
		response.getWriter().write(msgJson);
	}
}
