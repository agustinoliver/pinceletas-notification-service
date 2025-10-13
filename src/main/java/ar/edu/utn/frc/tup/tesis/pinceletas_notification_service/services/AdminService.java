package ar.edu.utn.frc.tup.tesis.pinceletas_notification_service.services;

import ar.edu.utn.frc.tup.tesis.pinceletas_notification_service.clients.UserServiceClient;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminService {

    private static final Logger log = LoggerFactory.getLogger(AdminService.class);

    private final UserServiceClient userServiceClient;

    public List<Long> obtenerIdsDeAdministradores() {
        log.info("🔍 Buscando IDs de administradores dinámicamente");

        List<Long> adminIds = new ArrayList<>();

        // Buscar el admin específico por email
        String adminEmail = "tomasherrado@gamil.com";
        UserServiceClient.UserBasicInfo admin = userServiceClient.getUserByEmail(adminEmail);

        if (admin != null && admin.id() != null) {
            adminIds.add(admin.id());
            log.info("✅ Admin encontrado: {} (ID: {})", adminEmail, admin.id());
        } else {
            log.warn("⚠️ No se encontró el admin con email: {}", adminEmail);
        }

        log.info("📋 IDs de administradores obtenidos: {}", adminIds.size());
        return adminIds;
    }
}
