package ar.edu.utn.frc.tup.tesis.pinceletas_notification_service.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CrearNotificacionRequest {

    private String titulo;
    private String mensaje;
    private String tipo;
    private Long usuarioId;
    private String metadata;


}

