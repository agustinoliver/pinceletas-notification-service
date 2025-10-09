package ar.edu.utn.frc.tup.tesis.pinceletas_notification_service.controllers;


import ar.edu.utn.frc.tup.tesis.pinceletas_notification_service.dtos.NotificacionDto;
import ar.edu.utn.frc.tup.tesis.pinceletas_notification_service.services.NotificacionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/notificaciones")
@RequiredArgsConstructor
@Tag(name = "Notificaciones", description = "API para gestión de notificaciones de usuarios")
@SecurityRequirement(name = "bearerAuth")
public class NotificacionController {

    private final NotificacionService notificacionService;

    @GetMapping
    @Operation(summary = "Obtener todas las notificaciones del usuario")
    public ResponseEntity<List<NotificacionDto>> obtenerMisNotificaciones(@AuthenticationPrincipal String email) {
        // En un escenario real, buscarías el usuarioId por email
        // Por ahora usamos un ID hardcodeado para testing
        Long usuarioId = obtenerUsuarioIdDesdeEmail(email);
        return ResponseEntity.ok(notificacionService.obtenerNotificacionesPorUsuario(usuarioId));
    }

    @GetMapping("/no-leidas")
    @Operation(summary = "Obtener notificaciones no leídas del usuario")
    public ResponseEntity<List<NotificacionDto>> obtenerNoLeidas(@AuthenticationPrincipal String email) {
        Long usuarioId = obtenerUsuarioIdDesdeEmail(email);
        return ResponseEntity.ok(notificacionService.obtenerNotificacionesNoLeidas(usuarioId));
    }

    @GetMapping("/contador-no-leidas")
    @Operation(summary = "Obtener contador de notificaciones no leídas")
    public ResponseEntity<Long> contarNoLeidas(@AuthenticationPrincipal String email) {
        Long usuarioId = obtenerUsuarioIdDesdeEmail(email);
        return ResponseEntity.ok(notificacionService.contarNotificacionesNoLeidas(usuarioId));
    }

    @PutMapping("/{id}/leer")
    @Operation(summary = "Marcar notificación como leída")
    public ResponseEntity<Void> marcarComoLeida(@PathVariable Long id, @AuthenticationPrincipal String email) {
        Long usuarioId = obtenerUsuarioIdDesdeEmail(email);
        notificacionService.marcarComoLeida(id, usuarioId);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/leer-todas")
    @Operation(summary = "Marcar todas las notificaciones como leídas")
    public ResponseEntity<Void> marcarTodasComoLeidas(@AuthenticationPrincipal String email) {
        Long usuarioId = obtenerUsuarioIdDesdeEmail(email);
        notificacionService.marcarTodasComoLeidas(usuarioId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar notificación")
    public ResponseEntity<Void> eliminarNotificacion(@PathVariable Long id, @AuthenticationPrincipal String email) {
        Long usuarioId = obtenerUsuarioIdDesdeEmail(email);
        notificacionService.eliminarNotificacion(id, usuarioId);
        return ResponseEntity.ok().build();
    }

    // Método temporal - en producción esto vendría de un servicio de usuarios
    private Long obtenerUsuarioIdDesdeEmail(String email) {
        // Por ahora, para testing, usamos un ID fijo
        // En producción, integrar con el servicio de usuarios
        return 1L;
    }
}
