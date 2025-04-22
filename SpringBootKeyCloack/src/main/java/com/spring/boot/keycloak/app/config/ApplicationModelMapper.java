package com.spring.boot.keycloak.app.config;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import lombok.extern.slf4j.Slf4j;
@Slf4j
@Configuration
@ComponentScan("com.spring.boot.keycloak.app")
public class ApplicationModelMapper  {

	  @Bean
	  public ModelMapper modelMapper() {
		log.info("[SPRING-BOOT-KEY-CLOACK] - MODELMAPPER - INICIALIZADO");
	    final ModelMapper mp = new ModelMapper();
	    mp.getConfiguration().setAmbiguityIgnored(true).setDeepCopyEnabled(false)
	        .setFullTypeMatchingRequired(true).setMatchingStrategy(MatchingStrategies.STRICT);
	    return mp;
	  }
	  
	  @Bean
	    public OpenAPI customOpenAPI() {
	        return new OpenAPI()
	                .components(new Components())
	                .info(new Info().title("Eduardo Nofre API").description(
	                        "This is a sample Spring Boot RESTful service using springdoc-openapi and OpenAPI 3."));
	    }
}
