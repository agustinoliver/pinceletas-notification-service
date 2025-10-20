package ar.edu.utn.frc.tup.tesis.pinceletas_notification_service.services;

import ar.edu.utn.frc.tup.tesis.pinceletas_notification_service.clients.UserServiceClient;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * Servicio para mapeo de usuarios entre servicios.
 * Proporciona funcionalidades para obtener información de usuarios desde el servicio de usuarios.
 */
@Service
@RequiredArgsConstructor
public class UserMappingService {

    private static final Logger log = LoggerFactory.getLogger(UserMappingService.class);

    /** Cliente para comunicación con el servicio de usuarios. */
    private final UserServiceClient userServiceClient;

    /**
     * Obtiene el ID de usuario a partir de su email.
     * Realiza una llamada al servicio de usuarios para obtener la información básica.
     *
     * @param email Email del usuario.
     * @return ID del usuario o null si no se encuentra.
     */
    public Long obtenerUserIdPorEmail(String email) {
        try {
            log.info("🔍 Buscando userId para email: {}", email);

            UserServiceClient.UserBasicInfo user = userServiceClient.getUserByEmail(email);

            if (user != null && user.id() != null) {
                log.info("✅ userId encontrado: {} para email: {}", user.id(), email);
                return user.id();
            } else {
                log.warn("⚠️ Usuario no encontrado para email: {}", email);
                return null;
            }
        } catch (Exception e) {
            log.error("❌ Error obteniendo userId para email {}: {}", email, e.getMessage());
            return null;
        }
    }
}
