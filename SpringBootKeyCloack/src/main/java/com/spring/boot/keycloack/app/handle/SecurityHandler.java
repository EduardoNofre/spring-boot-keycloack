package com.spring.boot.keycloack.app.handle;

import java.io.IOException;
import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;


@Component
public class SecurityHandler implements AccessDeniedHandler{


	@Override
	public void handle(jakarta.servlet.http.HttpServletRequest request,
			jakarta.servlet.http.HttpServletResponse response, AccessDeniedException accessDeniedException)
			throws IOException, jakarta.servlet.ServletException {
		
		HttpStatus status = HttpStatus.FORBIDDEN;
		String msg = "Acesso negado";
		response.addHeader("KEYCLOAK", msg);
		response.setStatus(status.value());
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		String msgJson = String.format("{\"error\": %s,\"message\": \"%s\",\"path\": \"%s\",\"status\": \"%s\",\"timestamp\": \"%s\"}",
				status.getReasonPhrase(),msg,"",status.value(),LocalDateTime.now().toString());
		response.getWriter().write(msgJson);
	}
}
