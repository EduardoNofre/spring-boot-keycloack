package com.spring.boot.keycloak.app.config.security;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Value;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;

import com.spring.boot.keycloak.app.handle.ErrorResponse;

import org.springframework.http.HttpHeaders;
import org.apache.commons.lang3.StringUtils;

/**
 * Filtro utilizado para exigir o cabecalho usuarioAcao em todas as requisicoes
 * da api
 *
 */
public class SecurityFilter {

	@Value("${server.servlet.context-path}")
	private String contexPath;

	private static final String GET = "GET";
	private static final String OPTIONS = "OPTIONS";
	private static final String CHARSET_UTF_8 = "UTF-8";
	private static final String HEADER_USUARIO_ACAO = "usuarioAcao";
	private static final String MSG = "Cabeçalho - 'usuarioAcao' é obrigatório";

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;
		String header = req.getHeader(HEADER_USUARIO_ACAO);

		// evita o CORS ORIGN
		res.setHeader("Access-Control-Allow-Origin", "*");
		res.setHeader("Access-Control-Allow-Methods", "POST, PUT, GET, PATCH, OPTIONS, DELETE");
		res.setHeader("Access-Control-Allow-Headers",
				"Authorization,Origin, X-Requested-With, Content-Type, Accept, Key");
		res.setHeader("Access-Control-Max-Age", "3600");
		req.setCharacterEncoding(CHARSET_UTF_8);
		res.setCharacterEncoding(CHARSET_UTF_8);

		if (req.getMethod().equals(GET)) {
			chain.doFilter(req, res);
		} else {
			if (!req.getMethod().equals(OPTIONS) && StringUtils.isBlank(header)) {
				res.resetBuffer();
				res.setStatus(HttpStatus.FORBIDDEN.value());
				res.setHeader(HttpHeaders.CONTENT_TYPE, "application/json");
				String retorno = new ErrorResponse(MSG, HttpStatus.FORBIDDEN).toString();
				res.getOutputStream().print(retorno);
				res.flushBuffer();
			} else {
				chain.doFilter(req, res);
			}
		}
	}
}
