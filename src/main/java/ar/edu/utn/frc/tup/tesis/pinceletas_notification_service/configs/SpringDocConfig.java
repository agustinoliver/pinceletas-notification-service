package ar.edu.utn.frc.tup.tesis.pinceletas_notification_service.configs;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.core.jackson.ModelResolver;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuración central para la documentación de Swagger / OpenAPI
 * Incluye soporte para JWT y datos de contacto del desarrollador.
 */
@Configuration
@SecurityScheme(
        name = "bearerAuth",
        type = SecuritySchemeType.HTTP,
        scheme = "bearer",
        bearerFormat = "JWT"
)
public class SpringDocConfig {

    @Value("${app.dev-name}")
    private String devName;

    @Value("${app.dev-email}")
    private String devEmail;

    @Bean
    public OpenAPI openApi(
            @Value("${app.name}") String appName,
            @Value("${app.desc}") String appDescription,
            @Value("${app.version}") String appVersion) {

        Info info = new Info()
                .title(appName)
                .version(appVersion)
                .description(appDescription)
                .contact(new Contact()
                        .name(devName)
                        .email(devEmail));

        return new OpenAPI()
                .components(new Components())
                .info(info)
                // 👇 Seguridad JWT aplicada globalmente
                .addSecurityItem(new SecurityRequirement().addList("bearerAuth"));
    }

    @Bean
    public ModelResolver modelResolver(ObjectMapper objectMapper) {
        return new ModelResolver(objectMapper);
    }
}
