package com.key.cloak.spring.boot.model;

import lombok.Data;

@Data
public class EnderecoModel {

	private int id;
	private int numero;
	private String rua;
	private String complemento;
	private String bairro;
	private String cidade;
}
