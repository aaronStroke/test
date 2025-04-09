package mx.loal.pharmacy_admin_api.config;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI farmaciaLoalOpenAPI() {
        return new OpenAPI()
            .info(new Info()
                .title("Farmacia LOAL API")
                .description("Servicio que contiene los métodos para administrar la farmacia LOAL")
                .version("1.0.0")
                .license(new License()
                    .name("Apache 2.0")
                    .url("https://www.google.com")))
            .externalDocs(new ExternalDocumentation()
                .description("Documentación de la API de la farmacia LOAL")
                .url("https://www.google.com"));
    }

}
