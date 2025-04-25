package com.spring.boot.keycloak.app.config.security;

import java.util.Base64;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import com.spring.boot.keycloak.app.handle.KeyCloackException;

import jakarta.servlet.http.HttpServletRequest;

@Component
public class SecurityHelper {

	private static final String HEADER_USUARIO_ACAO="usuarioAcao";
	private static final String MSG = "Cabeçalho - 'usuarioAcao' é obrigatório";
	

	private HttpServletRequest request;
	
	@Value("${rest.user.sistema}")
	private String usuarioSistema;
	
	@Value("${rest.user.password.sistema}")
	private String senhaUsuarioSistema;
	
	public SecurityHelper(HttpServletRequest request) {
		this.request = request;
	}
	
	
	/**
	 * Login do usuario entre API
	 * @return
	 */
	public String getUsarioAutenticado() {
		return getUsario().getUsername();
	}
	
	
	/**
	 * Usuário que esta executando a ação de inclusão, alteração, deleção
	 * @return
	 * @throws SinistroException
	 */
	public String getUsuarioAcao() throws KeyCloackException {
	   if (StringUtils.isBlank(request.getHeader(HEADER_USUARIO_ACAO))) {
		   throw new KeyCloackException(MSG,HttpStatus.CONFLICT);
	   } else {
		   return request.getHeader(HEADER_USUARIO_ACAO);
	   }
	}
	
	/**
	 * Cabecalho requisicao
	 * @return
	 */
	public HttpHeaders getAutorizacaoHeader() {
		String chave = usuarioSistema+":"+senhaUsuarioSistema;
		String encoding = Base64.getEncoder().encodeToString(chave.getBytes());
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.add("Authorization", "Basic "+encoding);
		return httpHeaders;
	}
	
	
	/**
	 * Instancia do objeto logado
	 * @return
	 */
	public User getUsario() {
		try {
			return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		}catch (Exception e) {
			throw new AccessDeniedException("Seu token expirou, favor logar novamente");
		}

	}
}
