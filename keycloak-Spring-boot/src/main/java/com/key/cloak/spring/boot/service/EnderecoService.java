package com.key.cloak.spring.boot.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.key.cloak.spring.boot.model.EnderecoModel;

@Service
public class EnderecoService {

	public List<EnderecoModel> consultaEndereco() {

		List<EnderecoModel> endereco = new ArrayList<>();

		EnderecoModel end1 = new EnderecoModel();		
		end1.setId(1);
		end1.setNumero(123);
		end1.setRua("Rua: cotia ");
		end1.setComplemento("sem complemento");
		end1.setBairro("Butantã");
		end1.setCidade("Cotia");		
		endereco.add(end1);
		
		EnderecoModel end2 = new EnderecoModel();		
		end2.setId(2);
		end2.setNumero(345);
		end2.setRua("Rua: santa barbara ");
		end2.setComplemento("perto do campo de futebol");
		end2.setBairro("Barretos");
		end2.setCidade("Aparecida");		
		endereco.add(end2);
		
		
		EnderecoModel end3 = new EnderecoModel();		
		end3.setId(2);
		end3.setNumero(345);
		end3.setRua("Rua: xana torta ");
		end3.setComplemento("luz vermelha");
		end3.setBairro("Augusta");
		end3.setCidade("São Paulo");		
		endereco.add(end3);
		
		return endereco;
	}
}
