package com.key.cloak.spring.boot.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.key.cloak.spring.boot.model.EnderecoModel;
import com.key.cloak.spring.boot.service.EnderecoService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

//@Api(value = "/v1/keycloack", description = "Responsável por keycloack")
@RestController
@Validated
@RequestMapping("/keycloack")
public class KeyCloackController {
	
	@Autowired
	private EnderecoService enderecoService;

	@ApiOperation(value = "TESTE SEM KEYCLOACK", response = ResponseEntity.class)
	@ApiResponses(value = { @ApiResponse(code = 400, message = "HttpStatus 400 = Falhas do keycloack."),
	@ApiResponse(code = 500, message = "Código da falha: 500 = Erro interno sem causa mapeada.") })
	@GetMapping(path = "/sem-keycloack", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<EnderecoModel>> buscaTodosEndereco() {
		
		List<EnderecoModel> endereco = enderecoService.consultaEndereco();

		return new ResponseEntity<>(endereco, HttpStatus.OK);
	}

	@ApiOperation(value = "TESTE COM KEYCLOACK", response = ResponseEntity.class)
	@ApiResponses(value = { @ApiResponse(code = 400, message = "HttpStatus 400 = Falhas do keycloack."),
	@ApiResponse(code = 500, message = "Código da falha: 500 = Erro interno sem causa mapeada.") })
	@GetMapping(path = "/com-keycloack", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> buscaComkeycloack() {

		String keycloackResponse = "Teste com keycloack";

		return new ResponseEntity<>(keycloackResponse, HttpStatus.OK);
	}
}
