package ar.edu.utn.frc.tup.tesis.pinceletas_notification_service.clients;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@RequiredArgsConstructor
public class UserServiceClient {

    private static final Logger log = LoggerFactory.getLogger(UserServiceClient.class);

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    @Value("${app.services.user-auth-service.url:https://pinceletas-user-auth.onrender.com}")
    private String userServiceUrl;

    public UserBasicInfo getUserByEmail(String email) {
        try {
            log.info("🔍 Llamando a user-service para email: {}", email);

            String url = userServiceUrl + "/api/users/by-email?email=" + email;
            ResponseEntity<UserBasicInfo> response = restTemplate.getForEntity(url, UserBasicInfo.class);

            if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
                log.info("✅ Respuesta recibida de user-service para email: {}", email);
                return response.getBody();
            } else {
                log.warn("⚠️ Usuario no encontrado en user-service para email: {}", email);
                return null;
            }
        } catch (Exception e) {
            log.error("❌ Error llamando a user-service para email {}: {}", email, e.getMessage());
            return null;
        }
    }

    public record UserBasicInfo(Long id, String email, String nombre, String apellido, String role) {}
}
