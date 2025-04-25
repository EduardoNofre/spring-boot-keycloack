package com.spring.boot.keycloak.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.spring.boot.keycloak.app.config.security.SecurityUserDetails;
import com.spring.boot.keycloak.app.dto.RefreshTokenDTO;
import com.spring.boot.keycloak.app.dto.UsuarioDTO;
import com.spring.boot.keycloak.app.service.UsuarioService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/keycloack")
@AllArgsConstructor
@Tag(name = "Recurso AuthController", description = "Auth Controller controle de usuarios")
public class UsuarioController {
	
	
	@Autowired
	private UsuarioService  usuarioService ;
	
	@Autowired
	private SecurityUserDetails securityUserDetails;

	@Operation(summary = "login ", description = "Login de usuario Spring Boo keycloack")
	@ApiResponses(value = { 
			@ApiResponse(responseCode = "200", description = "Sucesso", content = {	@Content(mediaType = "application/json", schema = @Schema(implementation = UsuarioDTO.class))}),
			@ApiResponse(responseCode = "400", description = "Erro processar a requisição", content = @Content),
			@ApiResponse(responseCode = "401", description = "Não autorizado", content = @Content),
			@ApiResponse(responseCode = "500", description = "Interno sem causa mapeada.", content = @Content),
			@ApiResponse(responseCode = "504", description = "Gateway Time-Out", content = @Content) })
	@PostMapping("/login")
	public ResponseEntity<UsuarioDTO> loginSpringBoot(@Parameter(name = "usuarioAcao", description = "Usuario logado", example = "eduardo.sa") @RequestHeader("usuarioAcao") String usuarioAcao,
			@Valid @RequestBody UsuarioDTO usuarioDTO) throws AccessDeniedException{

		return ResponseEntity.status(HttpStatus.OK).body(usuarioService.usuario(usuarioDTO));

	}
	
	
	@Operation(summary = "Refresh Token ", description = "Refresh Token Spring Boot keycloack")
	@ApiResponses(value = { 
			@ApiResponse(responseCode = "200", description = "Sucesso", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = RefreshTokenDTO.class)) }),
			@ApiResponse(responseCode = "400", description = "Erro processar a requisição", content = @Content),
			@ApiResponse(responseCode = "401", description = "Não autorizado", content = @Content),
			@ApiResponse(responseCode = "500", description = "Interno sem causa mapeada.", content = @Content),
			@ApiResponse(responseCode = "504", description = "Gateway Time-Out", content = @Content) })
	@PostMapping("/refresh-token")
	public ResponseEntity<RefreshTokenDTO> refreshTokenSpringBoot(String refreshToken) throws AccessDeniedException{

		return ResponseEntity.status(HttpStatus.OK).body(securityUserDetails.refreshTokenService(refreshToken));

	}
}
