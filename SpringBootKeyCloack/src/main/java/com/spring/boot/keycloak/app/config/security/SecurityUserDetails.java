package com.spring.boot.keycloak.app.config.security;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.google.gson.Gson;
import com.spring.boot.keycloak.app.config.HttpComponent;
import com.spring.boot.keycloak.app.dto.RefreshTokenDTO;
import com.spring.boot.keycloak.app.utils.HttpParamsUtil;

import io.swagger.v3.oas.annotations.Parameter;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@org.springframework.stereotype.Service
public class SecurityUserDetails  {

	@Value("${keycloak.resource}")
	private String clientId;

	@Value("${keycloak.credentials.secret}")
	private String clientSecret;

	@Value("${keycloak.user-login.grant-type}")
	private String grantType;

	@Value("${keycloak.auth-server-url}")
	private String keycloakServerUrl;

	@Value("${keycloak.realm}")
	private String realm;
	
	@Value("${rest.user.sistema}")
	private String usuario;

	@Value("${rest.user.password.sistema}")
	private String senha;
		
	@Value("${keycloak.recurso.role}")
	private String role_sistema;

	@Autowired
	private HttpComponent httpComponent;

	public UserDetailsService loginService()  throws AccessDeniedException{

		log.info("Login service inicio {}", LocalDateTime.now());		

		httpComponent.httpHeaders().setContentType(MediaType.APPLICATION_FORM_URLENCODED);

		MultiValueMap<String, String> params = HttpParamsUtil.builder()
				.HttpParamsClientId(clientId)
				.HttpParamsClientSecret(clientSecret)
				.HttpParamsGrantType(grantType)
				.HttpParamsUsername(usuario)
				.HttpParamsPassword(senha).build();

		try {

			String url = String.format("%s/protocol/openid-connect/token/", keycloakServerUrl);

			HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, httpComponent.httpHeaders());

			ResponseEntity<Map> response = restTemplate().exchange(url, HttpMethod.POST, request, Map.class);

			if (!response.getStatusCode().is2xxSuccessful()) {
				throw new AccessDeniedException(
						"Autênticação falhou - verificar as configurações do sistema de segurança");
			} else {
				log.info("Autenticando: {} para o recurso: {}",usuario,clientId);

				Map<String, Object> body = response.getBody();
				String token = (String) body.get("access_token");

				// Decodificar el token JWT
				DecodedJWT decodedJWT = JWT.decode(token);

				//valida username
				String userName = decodedJWT.getClaim("preferred_username").asString();
				if (!userName.equals(usuario)) {
					throw new AccessDeniedException("Usuário inválido");
				}


				//valida acessos
				Map<String, Object> recursos = decodedJWT.getClaim("resource_access").asMap();
				if (recursos == null || !recursos.containsKey("CLIENT_SPRING_BOOT")) {
					throw new AccessDeniedException("Usuário sem acesso");
				}


				//valida role
				Map<String, Object> acessos = (Map)recursos.get(clientId);
				List<String> roles = (List<String>) acessos.get("roles");
				String autorizacao = null;
				for (String role : roles) {
					if (role.equalsIgnoreCase(role_sistema)) {
						autorizacao = role_sistema;
						break;
					}
				}
				if (autorizacao==null) {
					throw new AccessDeniedException("Usuário sem acesso");
				}

				
				UserDetailsService user = new InMemoryUserDetailsManager(User.withUsername(usuario)
						.password(passwordEncoder().encode(senha))
						.roles(autorizacao).build());
				
				
				return user;
			}		

		} catch (Exception e) {
			throw new AccessDeniedException(e.getMessage());
		}
	}

	public RefreshTokenDTO refreshTokenService(
			@Parameter(name = "token", description = "token hash", example = "hbGciOiJIUzI1NiIsInR5cCIgOiAiS...") @RequestParam(name = "token", required = true )String token )  throws AccessDeniedException{

		log.info("Refresh Token service {}", LocalDateTime.now());		

		httpComponent.httpHeaders().setContentType(MediaType.APPLICATION_FORM_URLENCODED);

		MultiValueMap<String, String> map = HttpParamsUtil.builder()
				.HttpParamsClientId(clientId)
				.HttpParamsClientSecret(clientSecret)
				.HttpParamsGrantType("refresh_token")
				.HttpParamsTokenRefresh(token).build();

		HttpEntity<MultiValueMap<String, String>> rquest = new HttpEntity<>(map, httpComponent.httpHeaders());

		try {

			ResponseEntity<String> response = httpComponent.restTemplate().postForEntity(keycloakServerUrl + "/protocol/openid-connect/token/", rquest, String.class);

			RefreshTokenDTO responseObj = new Gson().fromJson(response.getBody(), RefreshTokenDTO.class);

			return responseObj;

		} catch (Exception e) {
			throw new AccessDeniedException("Autênticação falhou - Token não esta ativo");
		}

	}

	@Bean
	RestTemplate restTemplate() {
		return new RestTemplate();
	}

	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder(); 
	}
}
