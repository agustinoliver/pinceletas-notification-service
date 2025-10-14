package ar.edu.utn.frc.tup.tesis.pinceletas_notification_service.controllers;

import ar.edu.utn.frc.tup.tesis.pinceletas_notification_service.dtos.NotificacionDto;
import ar.edu.utn.frc.tup.tesis.pinceletas_notification_service.services.NotificacionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/notificaciones")
@Tag(name = "Notificaciones", description = "API para gestión de notificaciones de usuarios")
@RequiredArgsConstructor
public class NotificacionController {

    private final NotificacionService notificacionService;

    @GetMapping("/usuario/{usuarioId}")
    @Operation(summary = "Obtener todas las notificaciones de un usuario")
    public ResponseEntity<List<NotificacionDto>> obtenerNotificacionesPorUsuario(@PathVariable Long usuarioId) {
        return ResponseEntity.ok(notificacionService.obtenerNotificacionesPorUsuario(usuarioId));
    }

    @GetMapping("/usuario/{usuarioId}/no-leidas")
    @Operation(summary = "Obtener notificaciones no leídas de un usuario")
    public ResponseEntity<List<NotificacionDto>> obtenerNoLeidas(@PathVariable Long usuarioId) {
        return ResponseEntity.ok(notificacionService.obtenerNotificacionesNoLeidas(usuarioId));
    }

    @GetMapping("/usuario/{usuarioId}/contador-no-leidas")
    @Operation(summary = "Obtener contador de notificaciones no leídas")
    public ResponseEntity<Long> contarNoLeidas(@PathVariable Long usuarioId) {
        return ResponseEntity.ok(notificacionService.contarNotificacionesNoLeidas(usuarioId));
    }

    @PutMapping("/{id}/usuario/{usuarioId}/leer")
    @Operation(summary = "Marcar notificación como leída")
    public ResponseEntity<Void> marcarComoLeida(@PathVariable Long id, @PathVariable Long usuarioId) {
        notificacionService.marcarComoLeida(id, usuarioId);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/usuario/{usuarioId}/leer-todas")
    @Operation(summary = "Marcar todas las notificaciones como leídas")
    public ResponseEntity<Void> marcarTodasComoLeidas(@PathVariable Long usuarioId) {
        notificacionService.marcarTodasComoLeidas(usuarioId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}/usuario/{usuarioId}")
    @Operation(summary = "Eliminar notificación")
    public ResponseEntity<Void> eliminarNotificacion(@PathVariable Long id, @PathVariable Long usuarioId) {
        notificacionService.eliminarNotificacion(id, usuarioId);
        return ResponseEntity.ok().build();
    }
}