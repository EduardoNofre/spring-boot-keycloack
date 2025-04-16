package com.spring.boot.keycloack.app.service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;

import com.spring.boot.keycloack.app.config.HttpComponent;
import com.spring.boot.keycloack.app.model.Usuario;
import com.spring.boot.keycloack.app.utils.HttpParamsUtil;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Data
@Slf4j
@org.springframework.stereotype.Service
public class ServiceLogin {

	@Value("${keycloack.resource}")
	private String clientId = "";

	@Value("${keycloack.credentials.secret}")
	private String clientSecret;

	@Value("${keycloack.user-login.grant-type}")
	private String grantType;

	@Value("${keycloack.auth-server-url}")
	private String keycloackServerUrl;

	@Value("${keycloack.realm}")
	private String realm;

	@Autowired
	private HttpComponent httpComponent;

	public ResponseEntity<String> loginService(Usuario usuario) {

		log.info("Login service inicio {}", LocalDateTime.now());		
		
		httpComponent.httpHeaders().setContentType(MediaType.APPLICATION_FORM_URLENCODED);

		MultiValueMap<String, String> params = HttpParamsUtil.builder()
				.HttpParamsClientId(clientId)
				.HttpParamsClientSecret(clientSecret)
				.HttpParamsGrantType(grantType)
				.HttpParamsUsername(usuario.getUsuario())
				.HttpParamsPassword(usuario.getSenha()).build();

		HttpEntity<MultiValueMap<String, String>> rquest = new HttpEntity<>(params, httpComponent.httpHeaders());

		try {
			ResponseEntity<String> response = httpComponent.restTemplate().postForEntity(keycloackServerUrl + "/protocol/openid-connect/token/", rquest, String.class);
			return ResponseEntity.ok(response.getBody());
		} catch (HttpClientErrorException e) {
			return ResponseEntity.status(e.getStatusCode()).body(e.getMessage());
		}
	}
	
	public ResponseEntity<String> refreshTokenService(String token) {
		
		log.info("Refresh Token service {}", LocalDateTime.now());		
		
		httpComponent.httpHeaders().setContentType(MediaType.APPLICATION_FORM_URLENCODED);
	
		MultiValueMap<String, String> map = HttpParamsUtil.builder()
				.HttpParamsClientId(clientId)
				.HttpParamsClientSecret(clientSecret)
				.HttpParamsGrantType("refresh_token")
				.HttpParamsTokenRefresh(token).build();
		
		HttpEntity<MultiValueMap<String, String>> rquest = new HttpEntity<>(map, httpComponent.httpHeaders());
		
		try {
			
			ResponseEntity<String> response = httpComponent.restTemplate().postForEntity(keycloackServerUrl + "/protocol/openid-connect/token/", rquest, String.class);
			return ResponseEntity.ok(response.getBody());
			
		} catch (HttpClientErrorException e) {
			return ResponseEntity.status(e.getStatusCode()).body(e.getMessage());
		}
		
	}
}
