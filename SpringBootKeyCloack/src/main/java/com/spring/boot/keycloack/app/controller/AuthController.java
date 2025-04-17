package com.spring.boot.keycloack.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.spring.boot.keycloack.app.dto.BodyDTO;
import com.spring.boot.keycloack.app.dto.RefreshTokenDTO;
import com.spring.boot.keycloack.app.dto.UsuarioDTO;
import com.spring.boot.keycloack.app.service.ServiceLogin;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Getter;

@RestController
@RequestMapping("/keycloack")
@Getter
@AllArgsConstructor
@Order
@Tag(name = "Recurso AuthController", description = "Auth Controller controle de usuarios")
public class AuthController {
	
	
	@Autowired
	private ServiceLogin serviceLogin;

	@Operation(summary = "login ", description = "Login de usuario Spring Boo keycloack")
	@ApiResponses(value = { 
			@ApiResponse(responseCode = "200", description = "Sucesso", content = {	@Content(mediaType = "application/json", schema = @Schema(implementation = BodyDTO.class)) }),
			@ApiResponse(responseCode = "200", description = "sucesso", content = @Content),
			@ApiResponse(responseCode = "204", description = "Sem conteudo", content = @Content),
			@ApiResponse(responseCode = "400", description = "Erro processar a requisição", content = @Content),
			@ApiResponse(responseCode = "401", description = "Não autorizado", content = @Content),
			@ApiResponse(responseCode = "404", description = "Pagina não encontrado", content = @Content),
			@ApiResponse(responseCode = "500", description = "Interno sem causa mapeada.", content = @Content),
			@ApiResponse(responseCode = "504", description = "Gateway Time-Out", content = @Content) })
	@PostMapping("/login")
	public ResponseEntity<BodyDTO> loginSpringBoot(@Valid @RequestBody UsuarioDTO usuarioDTO) {

		return ResponseEntity.status(HttpStatus.OK).body(serviceLogin.loginService(usuarioDTO));

	}
	
	
	@Operation(summary = "Refresh Token ", description = "Refresh Token Spring Boot keycloack")
	@ApiResponses(value = { 
			@ApiResponse(responseCode = "200", description = "Sucesso", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = RefreshTokenDTO.class)) }),
			@ApiResponse(responseCode = "200", description = "sucesso", content = @Content),
			@ApiResponse(responseCode = "204", description = "Sem conteudo", content = @Content),
			@ApiResponse(responseCode = "400", description = "Erro processar a requisição", content = @Content),
			@ApiResponse(responseCode = "401", description = "Não autorizado", content = @Content),
			@ApiResponse(responseCode = "404", description = "Pagina não encontrado", content = @Content),
			@ApiResponse(responseCode = "500", description = "Interno sem causa mapeada.", content = @Content),
			@ApiResponse(responseCode = "504", description = "Gateway Time-Out", content = @Content) })
	@PostMapping("/refresh-token")
	public ResponseEntity<RefreshTokenDTO> refreshTokenSpringBoot(String refreshToken) {

		return ResponseEntity.status(HttpStatus.OK).body(serviceLogin.refreshTokenService(refreshToken));

	}
}
