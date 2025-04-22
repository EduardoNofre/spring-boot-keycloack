package com.spring.boot.keycloak.app.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import lombok.extern.slf4j.Slf4j;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.info.BuildProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
@OpenAPIDefinition
public class OpenApiConfig {

	
	@Autowired
	BuildProperties buildProperties;

    @Value("${server.servlet.context-path}")
    private String path;

    static final String SECURITY_SCHEME_NAME = "Basic";


    /**
     * Open API Configuration Bean
     *
     * @param title
     * @param version
     * @param description
     * @return
     */
    @Bean
    OpenAPI openApiConfiguration() {
        log.info("[OpenApiConfig - iniciado]");
        return new OpenAPI()
                .addServersItem(new Server().url(path))
                .addSecurityItem(new SecurityRequirement().addList(SECURITY_SCHEME_NAME))
                .components(
                        new Components()
                                .addSecuritySchemes(SECURITY_SCHEME_NAME,
                                        new SecurityScheme()
                                                .name(SECURITY_SCHEME_NAME)
                                                .type(SecurityScheme.Type.HTTP)
                                                .scheme("basic")
                                )
                )
                .info(new Info()
                        .title(buildProperties.getName())
                        .version(buildProperties.getVersion())
                        .description(buildProperties.getGroup())
                        .license(getLicense())

                );
    }


    /**
     * License creation
     *
     * @return
     */
    private License getLicense() {
        License license = new License();
        license.setExtensions(Collections.emptyMap());
        return license;
    }
}
