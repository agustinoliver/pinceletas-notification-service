package ar.edu.utn.frc.tup.tesis.pinceletas_notification_service.repositories;


import ar.edu.utn.frc.tup.tesis.pinceletas_notification_service.entities.NotificacionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repositorio para operaciones de base de datos con notificaciones.
 * Proporciona queries personalizadas para operaciones comunes de notificaciones.
 */
@Repository
public interface NotificacionRepository extends JpaRepository<NotificacionEntity, Long> {

    /**
     * Encuentra todas las notificaciones de un usuario ordenadas por fecha descendente.
     *
     * @param usuarioId ID del usuario.
     * @return Lista de notificaciones ordenadas por fecha de creación descendente.
     */
    List<NotificacionEntity> findByUsuarioIdOrderByFechaCreacionDesc(Long usuarioId);

    /**
     * Encuentra notificaciones no leídas de un usuario ordenadas por fecha descendente.
     *
     * @param usuarioId ID del usuario.
     * @param estado Estado de lectura (normalmente "NO_LEIDA").
     * @return Lista de notificaciones no leídas ordenadas.
     */
    List<NotificacionEntity> findByUsuarioIdAndEstadoOrderByFechaCreacionDesc(Long usuarioId, String estado);

    /**
     * Cuenta las notificaciones no leídas de un usuario.
     *
     * @param usuarioId ID del usuario.
     * @param estado Estado de lectura (normalmente "NO_LEIDA").
     * @return Número de notificaciones no leídas.
     */
    Long countByUsuarioIdAndEstado(Long usuarioId, String estado);

    /**
     * Marca una notificación específica como leída.
     * Query de actualización que también establece la fecha de lectura.
     *
     * @param id ID de la notificación.
     * @param usuarioId ID del usuario (para validación de propiedad).
     */
    @Modifying
    @Query("UPDATE NotificacionEntity n SET n.estado = 'LEIDA', n.fechaLectura = CURRENT_TIMESTAMP WHERE n.id = :id AND n.usuarioId = :usuarioId")
    void marcarComoLeida(@Param("id") Long id, @Param("usuarioId") Long usuarioId);

    /**
     * Marca todas las notificaciones no leídas de un usuario como leídas.
     *
     * @param usuarioId ID del usuario.
     */
    @Modifying
    @Query("UPDATE NotificacionEntity n SET n.estado = 'LEIDA', n.fechaLectura = CURRENT_TIMESTAMP WHERE n.usuarioId = :usuarioId AND n.estado = 'NO_LEIDA'")
    void marcarTodasComoLeidas(@Param("usuarioId") Long usuarioId);

    /**
     * Elimina una notificación específica validando la propiedad por usuario.
     *
     * @param id ID de la notificación.
     * @param usuarioId ID del usuario propietario.
     */
    void deleteByIdAndUsuarioId(Long id, Long usuarioId);
}
