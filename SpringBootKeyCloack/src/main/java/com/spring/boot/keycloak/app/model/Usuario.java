package com.spring.boot.keycloak.app.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class Usuario {

	@NotBlank(message = "O campo não pode estar vazio")
	private String usuario;

	@Size(min = 6, max = 20, message = "A senha dever no minimo 6 e no maximo 20 carateres ")
	private String senha;
}
