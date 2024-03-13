package com.dontforget.dontforget.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI springShopOpenAPI() {
        return new OpenAPI()
            .components(new Components())
            .addServersItem(new Server().url("/"))
            .info(new Info().title("챙겨챙겨 API")
                .description("챙겨챙겨 프로젝트 API 명세서입니다.")
                .version("v0.0.1")
            );
    }
}
