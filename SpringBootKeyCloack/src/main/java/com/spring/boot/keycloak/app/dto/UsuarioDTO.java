package com.spring.boot.keycloak.app.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UsuarioDTO {

	@Schema(name = "usuario", description = "Nome do usuario", example = "eduardo_admin", type = "String")
	@NotBlank(message = "O campo não pode estar vazio")
	private String usuario;

	@Schema(name = "senha", description = "senha do usuario", example = "*Edu*147258", type = "Long")
	@Size(min = 6, max = 20, message = "A senha dever no minimo 6 e no maximo 20 carateres ")
	private String senha;
	
	
	@Schema(name = "teste", description = "teste", example = "*Edu*147258", type = "Long")
	@Size(min = 6, max = 20, message = "teste")
	private String teste;
}
