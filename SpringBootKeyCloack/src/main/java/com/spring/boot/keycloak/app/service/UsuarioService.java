package com.spring.boot.keycloak.app.service;

import org.springframework.stereotype.Service;

import com.spring.boot.keycloak.app.dto.UsuarioDTO;

@Service
public class UsuarioService {

	public UsuarioDTO usuario(UsuarioDTO usuarioDTO) {
		
		usuarioDTO.setTeste("Teste Eduardo Keycloak");
		return usuarioDTO;
	}
}
