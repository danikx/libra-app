package kz.netcracker.libra.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI libraryApi() {
        return new OpenAPI()
                .info(new Info()
                        .title("Library API")
                        .description("API for managing library books and authors")
                        .version("1.0")
                        .contact(new Contact()
                                .name("Daniyar Kalmurzin")
                                .email("daniyar.kalmurzin@gmail.com")));
    }
}