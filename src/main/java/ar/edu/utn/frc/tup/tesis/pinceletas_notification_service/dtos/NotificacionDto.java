package ar.edu.utn.frc.tup.tesis.pinceletas_notification_service.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

/**
 * DTO para representación de notificaciones en respuestas API.
 * Contiene todos los datos de una notificación incluyendo fechas y estado.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class NotificacionDto {

    /** Identificador único de la notificación. */
    private Long id;
    /** Título de la notificación. */
    private String titulo;
    /** Mensaje detallado de la notificación. */
    private String mensaje;
    /** Tipo de notificación. */
    private String tipo;
    /** Estado de lectura (LEIDA/NO_LEIDA). */
    private String estado;
    /** ID del usuario propietario. */
    private Long usuarioId;
    /** Metadatos adicionales en formato JSON. */
    private String metadata;
    /** Fecha y hora de creación de la notificación. */
    private LocalDateTime fechaCreacion;
    /** Fecha y hora de lectura de la notificación (null si no leída). */
    private LocalDateTime fechaLectura;


}

