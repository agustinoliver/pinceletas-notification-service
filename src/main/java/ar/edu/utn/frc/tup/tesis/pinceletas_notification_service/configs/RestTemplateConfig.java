package ar.edu.utn.frc.tup.tesis.pinceletas_notification_service.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * Configuración de RestTemplate para llamadas HTTP a servicios externos.
 * Utilizado principalmente para comunicación con el servicio de usuarios.
 */
@Configuration
public class RestTemplateConfig {

    /**
     * Bean de RestTemplate para realizar peticiones HTTP.
     * Configuración básica sin autenticación adicional.
     *
     * @return RestTemplate listo para usar.
     */
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

}
