package com.spring.boot.keycloack.app.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BodyDTO {

	@Schema(name = "access_token", description = "token da keycloack", example = "IUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICIyMDE5ZjdhMi1l...", type = "String")
	private String access_token;
	
	@Schema(name = "expires_in", description = "Numero do processo", example = "300", type = "String")
	private String expires_in;
	
	@Schema(name = "refresh_expires_in", description = "Tempo de expiração do token", example = "1800", type = "String")
	private String refresh_expires_in;
	
	@Schema(name = "refresh_token", description = "atualização do token", example = "eyJhbGciOiJIUzI1NiIsInR5cCIgOiAi....", type = "String")
	private String refresh_token;
	
	@Schema(name = "token_type", description = "Tipo de token", example = "Bearer", type = "String")
	private String token_type;
	
	@Schema(name = "session_state", description = "Estado da seção", example = "02527eab-a37e-4db3-ac91-c9...", type = "String")
	private String session_state;
	
	@Schema(name = "scope", description = "Escopo", example = "email profile", type = "String")
	private String scope;
}
