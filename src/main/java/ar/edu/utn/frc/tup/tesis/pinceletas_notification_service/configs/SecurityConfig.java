package ar.edu.utn.frc.tup.tesis.pinceletas_notification_service.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

/**
 * Configuración de seguridad para el servicio de notificaciones.
 * Define políticas CORS, CSRF y autorización de endpoints.
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    /**
     * Configura la cadena de filtros de seguridad.
     * Permite acceso público a todos los endpoints de notificaciones y documentación.
     *
     * @param http Configurador de seguridad HTTP.
     * @return SecurityFilterChain configurado.
     * @throws Exception Si ocurre error en la configuración.
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/swagger-ui/**", "/v3/api-docs/**").permitAll()
                        // 🔥 PERMITIR TODOS LOS MÉTODOS HTTP PARA NOTIFICACIONES
                        .requestMatchers(HttpMethod.GET, "/notificaciones/**").permitAll()
                        .requestMatchers(HttpMethod.PUT, "/notificaciones/**").permitAll()
                        .requestMatchers(HttpMethod.DELETE, "/notificaciones/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/notificaciones/**").permitAll()
                        .requestMatchers("/health", "/actuator/health").permitAll()
                        .anyRequest().authenticated()
                )
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        return http.build();
    }

    /**
     * Configuración CORS para permitir requests desde el frontend.
     *
     * @return CorsConfigurationSource con políticas CORS definidas.
     */
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins(List.of("https://pinceletas-frontend.onrender.com"));
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH"));
        config.setAllowedHeaders(List.of("*"));
        config.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }
}