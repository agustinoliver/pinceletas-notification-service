package ar.edu.utn.frc.tup.tesis.pinceletas_notification_service.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

/**
 * Entidad que representa una notificación en el sistema.
 * Almacena información de notificaciones para usuarios con estado de lectura.
 */
@Entity
@Table(name = "notificaciones")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class NotificacionEntity {

    /** Identificador único de la notificación en la base de datos. */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** Título de la notificación. No puede ser nulo. */
    @Column(nullable = false)
    private String titulo;

    /** Mensaje detallado de la notificación. Longitud máxima 1000 caracteres. */
    @Column(nullable = false, length = 1000)
    private String mensaje;

    /** Tipo de notificación (INICIO_SESION, BIENVENIDA_REGISTRO, etc.). */
    @Column(nullable = false)
    private String tipo;

    /** Estado de lectura. Por defecto "NO_LEIDA". */
    @Column(nullable = false)
    private String estado = "NO_LEIDA";

    /** ID del usuario destinatario de la notificación. */
    @Column(nullable = false)
    private Long usuarioId;

    /** Metadatos adicionales en formato JSON. */
    private String metadata;

    /** Fecha y hora de creación automática (timestamp). */
    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime fechaCreacion;

    /** Fecha y hora de lectura (null si no ha sido leída). */
    private LocalDateTime fechaLectura;

    /**
     * Constructor para crear notificaciones fácilmente sin ID ni fechas.
     *
     * @param titulo Título de la notificación.
     * @param mensaje Mensaje detallado.
     * @param tipo Tipo de notificación.
     * @param usuarioId ID del usuario destinatario.
     * @param metadata Metadatos adicionales.
     */
    public NotificacionEntity(String titulo, String mensaje, String tipo, Long usuarioId, String metadata) {
        this.titulo = titulo;
        this.mensaje = mensaje;
        this.tipo = tipo;
        this.usuarioId = usuarioId;
        this.metadata = metadata;
        this.estado = "NO_LEIDA";
    }
}