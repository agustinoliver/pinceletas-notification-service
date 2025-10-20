package ar.edu.utn.frc.tup.tesis.pinceletas_notification_service.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO para solicitud de creación de notificaciones.
 * Utilizado para crear notificaciones manualmente o mediante eventos.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CrearNotificacionRequest {

    /** Título de la notificación. */
    private String titulo;
    /** Mensaje detallado de la notificación. */
    private String mensaje;
    /** Tipo de notificación (INICIO_SESION, BIENVENIDA_REGISTRO, etc.). */
    private String tipo;
    /** ID del usuario destinatario. */
    private Long usuarioId;
    /** Metadatos adicionales en formato JSON. */
    private String metadata;

}

