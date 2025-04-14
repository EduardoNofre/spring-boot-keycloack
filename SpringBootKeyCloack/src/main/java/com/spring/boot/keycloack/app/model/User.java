package com.spring.boot.keycloack.app.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class User {

	@NotBlank(message = "O campo não pode estar vazio")
	private String user;

	@Size(min = 6, max = 20, message = "A senha dever no minimo 6 e no maximo 20 carateres ")
	private String password;
}
