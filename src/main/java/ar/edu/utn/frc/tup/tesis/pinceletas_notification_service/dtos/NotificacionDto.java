package ar.edu.utn.frc.tup.tesis.pinceletas_notification_service.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NotificacionDto {

    private Long id;
    private String titulo;
    private String mensaje;
    private String tipo;
    private String estado;
    private Long usuarioId;
    private String metadata;
    private LocalDateTime fechaCreacion;
    private LocalDateTime fechaLectura;


}

