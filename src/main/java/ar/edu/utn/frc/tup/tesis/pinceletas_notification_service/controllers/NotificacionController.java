package ar.edu.utn.frc.tup.tesis.pinceletas_notification_service.controllers;

import ar.edu.utn.frc.tup.tesis.pinceletas_notification_service.dtos.NotificacionDto;
import ar.edu.utn.frc.tup.tesis.pinceletas_notification_service.services.NotificacionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador REST para gestión de notificaciones de usuarios.
 * Proporciona endpoints para obtener, marcar y eliminar notificaciones.
 */
@RestController
@RequestMapping("/notificaciones")
@Tag(name = "Notificaciones", description = "API para gestión de notificaciones de usuarios")
@RequiredArgsConstructor
public class NotificacionController {

    /** Servicio de notificaciones para procesar las operaciones. */
    private final NotificacionService notificacionService;

    /**
     * Obtiene todas las notificaciones de un usuario, ordenadas por fecha de creación descendente.
     *
     * @param usuarioId ID del usuario.
     * @return Lista de NotificacionDto con todas las notificaciones del usuario.
     */
    @GetMapping("/usuario/{usuarioId}")
    @Operation(summary = "Obtener todas las notificaciones de un usuario")
    public ResponseEntity<List<NotificacionDto>> obtenerNotificacionesPorUsuario(@PathVariable Long usuarioId) {
        return ResponseEntity.ok(notificacionService.obtenerNotificacionesPorUsuario(usuarioId));
    }

    /**
     * Obtiene las notificaciones no leídas de un usuario.
     *
     * @param usuarioId ID del usuario.
     * @return Lista de NotificacionDto con notificaciones no leídas.
     */
    @GetMapping("/usuario/{usuarioId}/no-leidas")
    @Operation(summary = "Obtener notificaciones no leídas de un usuario")
    public ResponseEntity<List<NotificacionDto>> obtenerNoLeidas(@PathVariable Long usuarioId) {
        return ResponseEntity.ok(notificacionService.obtenerNotificacionesNoLeidas(usuarioId));
    }

    /**
     * Obtiene el contador de notificaciones no leídas de un usuario.
     *
     * @param usuarioId ID del usuario.
     * @return Número de notificaciones no leídas.
     */
    @GetMapping("/usuario/{usuarioId}/contador-no-leidas")
    @Operation(summary = "Obtener contador de notificaciones no leídas")
    public ResponseEntity<Long> contarNoLeidas(@PathVariable Long usuarioId) {
        return ResponseEntity.ok(notificacionService.contarNotificacionesNoLeidas(usuarioId));
    }

    /**
     * Marca una notificación específica como leída.
     *
     * @param id ID de la notificación.
     * @param usuarioId ID del usuario (para validación de propiedad).
     */
    @PutMapping("/{id}/usuario/{usuarioId}/leer")
    @Operation(summary = "Marcar notificación como leída")
    public ResponseEntity<Void> marcarComoLeida(@PathVariable Long id, @PathVariable Long usuarioId) {
        notificacionService.marcarComoLeida(id, usuarioId);
        return ResponseEntity.ok().build();
    }

    /**
     * Marca todas las notificaciones de un usuario como leídas.
     *
     * @param usuarioId ID del usuario.
     */
    @PutMapping("/usuario/{usuarioId}/leer-todas")
    @Operation(summary = "Marcar todas las notificaciones como leídas")
    public ResponseEntity<Void> marcarTodasComoLeidas(@PathVariable Long usuarioId) {
        notificacionService.marcarTodasComoLeidas(usuarioId);
        return ResponseEntity.ok().build();
    }

    /**
     * Elimina una notificación específica de un usuario.
     *
     * @param id ID de la notificación.
     * @param usuarioId ID del usuario (para validación de propiedad).
     */
    @DeleteMapping("/{id}/usuario/{usuarioId}")
    @Operation(summary = "Eliminar notificación")
    public ResponseEntity<Void> eliminarNotificacion(@PathVariable Long id, @PathVariable Long usuarioId) {
        notificacionService.eliminarNotificacion(id, usuarioId);
        return ResponseEntity.ok().build();
    }

    //    // 🔥 NUEVO: Endpoint para crear notificaciones manualmente (útil para testing)
    //    @PostMapping
    //    @Operation(summary = "Crear notificación manualmente")
    //    public ResponseEntity<NotificacionDto> crearNotificacion(@RequestBody ar.edu.utn.frc.tup.tesis.pinceletas_notification_service.dtos.CrearNotificacionRequest request) {
    //        return ResponseEntity.ok(notificacionService.crearNotificacion(request));
    //    }
}