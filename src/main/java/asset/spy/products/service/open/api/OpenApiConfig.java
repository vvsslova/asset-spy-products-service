package asset.spy.products.service.open.api;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Asset-spy-products-service")
                        .description("Service for registration of vendors and accounting of their products")
                        .version("1.0.0"))
                .components(new Components()
                        .addSecuritySchemes("jwt-bearer",
                                new SecurityScheme()
                                        .name("jwt-bearer")
                                        .type(SecurityScheme.Type.HTTP)
                                        .scheme("bearer")
                                        .bearerFormat("JWT"))
                );
    }
}
