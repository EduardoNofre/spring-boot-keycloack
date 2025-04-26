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
    
    
    @Value("${rest.security.schema.name}")
    private String security_scheme_name;


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
                .addSecurityItem(new SecurityRequirement().addList(security_scheme_name))
                .components(
                        new Components()
                                .addSecuritySchemes(security_scheme_name,
                                        new SecurityScheme()
                                                .name(security_scheme_name)
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

    private SecurityScheme createBearerScheme() {
        return new SecurityScheme()
                .type(SecurityScheme.Type.HTTP)
                .scheme(security_scheme_name);
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
