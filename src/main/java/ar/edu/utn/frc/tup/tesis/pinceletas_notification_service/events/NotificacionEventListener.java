package ar.edu.utn.frc.tup.tesis.pinceletas_notification_service.events;

import ar.edu.utn.frc.tup.tesis.pinceletas_notification_service.services.AdminService;
import ar.edu.utn.frc.tup.tesis.pinceletas_notification_service.services.NotificacionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

/**
 * Consumidor de eventos de notificación desde RabbitMQ.
 * Escucha la cola de notificaciones y procesa los eventos según su tipo y destino.
 */
@Component
public class NotificacionEventListener {

    private static final Logger log = LoggerFactory.getLogger(NotificacionEventListener.class);

    /** Servicio de notificaciones para almacenamiento. */
    private final NotificacionService notificacionService;
    /** Servicio de administradores para notificaciones broadcast. */
    private final AdminService adminService;

    public NotificacionEventListener(NotificacionService notificacionService,
                                     AdminService adminService) {
        this.notificacionService = notificacionService;
        this.adminService = adminService;
    }

    /**
     * Escucha y procesa eventos de notificación desde RabbitMQ.
     * Distingue entre notificaciones para usuarios individuales y broadcast para administradores.
     *
     * @param event Evento de notificación recibido.
     */
    @RabbitListener(queues = "notificaciones.queue")
    public void recibirNotificacion(NotificacionEvent event) {
        log.info("🎯 Evento recibido de RabbitMQ - Tipo: {}, Target: {}", event.getTipo(), event.getTargetRole());
        log.debug("📦 Contenido del evento: {}", event);

        try {
            if ("USER".equals(event.getTargetRole()) && event.getUsuarioId() != null) {
                // ✅ Notificación para USUARIO específico
                // Casos: INICIO_SESION, INICIO_SESION_FIREBASE, BIENVENIDA_REGISTRO, ESTADO_PEDIDO
                notificacionService.procesarEventoNotificacion(
                        event.getTitulo(),
                        event.getMensaje(),
                        event.getTipo(),
                        event.getUsuarioId(),
                        event.getMetadata()
                );
                log.info("✅ Notificacion USER procesada para usuario: {}", event.getUsuarioId());
            }
            else if ("ADMIN".equals(event.getTargetRole())) {
                // ✅ Notificación para TODOS los ADMINS
                // Casos: NUEVO_REGISTRO, INICIO_SESION_FIREBASE_ADMIN, NUEVO_PEDIDO
                enviarNotificacionATodosLosAdmins(event);
                log.info("✅ Notificacion ADMIN procesada");
            }
            else {
                log.warn("⚠️ TargetRole no reconocido o usuarioId nulo: {}", event.getTargetRole());
            }
        } catch (Exception e) {
            log.error("❌ Error procesando notificacion {}: {}", event.getTipo(), e.getMessage(), e);
        }
    }

    /**
     * Envía una notificación a todos los administradores del sistema.
     * Obtiene la lista de IDs de administradores y crea una notificación para cada uno.
     *
     * @param event Evento de notificación a distribuir.
     */
    private void enviarNotificacionATodosLosAdmins(NotificacionEvent event) {
        List<Long> adminUserIds = adminService.obtenerIdsDeAdministradores();

        if (adminUserIds.isEmpty()) {
            log.info("👨‍💼 No hay administradores registrados para enviar notificación");
            return;
        }

        log.info("👨‍💼 Enviando notificación a {} administradores", adminUserIds.size());

        for (Long adminId : adminUserIds) {
            // ✅ Cada admin recibe la notificación con SU propio ID
            notificacionService.procesarEventoNotificacion(
                    event.getTitulo(),
                    event.getMensaje(),
                    event.getTipo(),
                    adminId,
                    event.getMetadata()
            );
        }
    }
}
