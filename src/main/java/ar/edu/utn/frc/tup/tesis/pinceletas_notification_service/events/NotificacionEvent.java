package ar.edu.utn.frc.tup.tesis.pinceletas_notification_service.events;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NotificacionEvent {

    private String titulo;
    private String mensaje;
    private String tipo; // BIENVENIDA, NUEVO_REGISTRO, etc.
    private Long usuarioId;
    private String metadata; // JSON con datos adicionales
    private String targetRole; // USER, ADMIN, ALL - para filtrar destinatarios
}
