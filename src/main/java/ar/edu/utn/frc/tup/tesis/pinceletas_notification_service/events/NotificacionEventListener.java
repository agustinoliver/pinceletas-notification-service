package ar.edu.utn.frc.tup.tesis.pinceletas_notification_service.events;

import ar.edu.utn.frc.tup.tesis.pinceletas_notification_service.repositories.NotificacionRepository;
import ar.edu.utn.frc.tup.tesis.pinceletas_notification_service.services.NotificacionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class NotificacionEventListener {

    private final NotificacionService notificacionService;
    private final NotificacionRepository notificacionRepository;

    @RabbitListener(queues = "${spring.rabbitmq.template.default-receive-queue:notificaciones.queue}")
    public void recibirNotificacion(NotificacionEvent event) {
        log.info("Evento recibido de RabbitMQ: {}", event);

        try {
            // Para notificaciones dirigidas a USER específico
            if ("USER".equals(event.getTargetRole()) && event.getUsuarioId() != null) {
                notificacionService.procesarEventoNotificacion(
                        event.getTitulo(),
                        event.getMensaje(),
                        event.getTipo(),
                        event.getUsuarioId(),
                        event.getMetadata()
                );
                log.info("Notificación USER procesada para usuario: {}", event.getUsuarioId());
            }

            // Para notificaciones dirigidas a ADMIN
            else if ("ADMIN".equals(event.getTargetRole())) {
                enviarNotificacionATodosLosAdmins(event);
                log.info("Notificación ADMIN procesada");
            }

            // Para notificaciones dirigidas a todos
            else if ("ALL".equals(event.getTargetRole())) {
                // Implementar lógica para todos los usuarios si es necesario
                log.info("Notificación ALL recibida: {}", event.getTitulo());
            }

        } catch (Exception e) {
            log.error("Error procesando notificación {}: {}", event.getTipo(), e.getMessage());
        }
    }

    private void enviarNotificacionATodosLosAdmins(NotificacionEvent event) {
        // Aquí necesitarías integrar con el servicio de usuarios para obtener IDs de admin
        // Por ahora, usaremos un ID hardcodeado para testing
        List<Long> adminUserIds = obtenerIdsDeAdministradores();

        for (Long adminId : adminUserIds) {
            notificacionService.procesarEventoNotificacion(
                    event.getTitulo(),
                    event.getMensaje(),
                    event.getTipo(),
                    adminId,
                    event.getMetadata()
            );
        }
    }

    // Método temporal - en producción esto vendría de User Service
    private List<Long> obtenerIdsDeAdministradores() {
        // Por ahora devolvemos una lista con ID 1 (admin por defecto)
        // En producción, harías una llamada HTTP al user-service o tendrías una base compartida
        return Arrays.asList(1L);
    }
}
