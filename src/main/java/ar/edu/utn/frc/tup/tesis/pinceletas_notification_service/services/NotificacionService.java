package ar.edu.utn.frc.tup.tesis.pinceletas_notification_service.services;

import ar.edu.utn.frc.tup.tesis.pinceletas_notification_service.dtos.CrearNotificacionRequest;
import ar.edu.utn.frc.tup.tesis.pinceletas_notification_service.dtos.NotificacionDto;
import ar.edu.utn.frc.tup.tesis.pinceletas_notification_service.entities.NotificacionEntity;
import ar.edu.utn.frc.tup.tesis.pinceletas_notification_service.repositories.NotificacionRepository;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Servicio principal para gestión de notificaciones.
 * Proporciona operaciones CRUD completas y procesamiento de eventos de notificación.
 */
@Service
public class NotificacionService {

    private static final Logger log = LoggerFactory.getLogger(NotificacionService.class);

    /** Repositorio para operaciones de base de datos con notificaciones. */
    private final NotificacionRepository notificacionRepository;
    /** Mapeador para conversiones entre entidades y DTOs. */
    private final ModelMapper modelMapper;

    public NotificacionService(NotificacionRepository notificacionRepository,
                               ModelMapper modelMapper) {
        this.notificacionRepository = notificacionRepository;
        this.modelMapper = modelMapper;
    }

    /**
     * Obtiene todas las notificaciones de un usuario ordenadas por fecha descendente.
     *
     * @param usuarioId ID del usuario.
     * @return Lista de DTOs de notificaciones.
     */
    public List<NotificacionDto> obtenerNotificacionesPorUsuario(Long usuarioId) {
        log.info("Obteniendo notificaciones para usuario: {}", usuarioId);
        return notificacionRepository.findByUsuarioIdOrderByFechaCreacionDesc(usuarioId)
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    /**
     * Obtiene las notificaciones no leídas de un usuario.
     *
     * @param usuarioId ID del usuario.
     * @return Lista de DTOs de notificaciones no leídas.
     */
    public List<NotificacionDto> obtenerNotificacionesNoLeidas(Long usuarioId) {
        log.info("Obteniendo notificaciones no leidas para usuario: {}", usuarioId);
        return notificacionRepository.findByUsuarioIdAndEstadoOrderByFechaCreacionDesc(usuarioId, "NO_LEIDA")
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    /**
     * Cuenta las notificaciones no leídas de un usuario.
     *
     * @param usuarioId ID del usuario.
     * @return Número de notificaciones no leídas.
     */
    public Long contarNotificacionesNoLeidas(Long usuarioId) {
        log.info("Contando notificaciones no leidas para usuario: {}", usuarioId);
        return notificacionRepository.countByUsuarioIdAndEstado(usuarioId, "NO_LEIDA");
    }

    /**
     * Crea una nueva notificación en el sistema.
     *
     * @param request Datos para crear la notificación.
     * @return DTO de la notificación creada.
     */
    @Transactional
    public NotificacionDto crearNotificacion(CrearNotificacionRequest request) {
        log.info("Creando notificacion: {} para usuario: {}", request.getTitulo(), request.getUsuarioId());

        NotificacionEntity notificacion = new NotificacionEntity(
                request.getTitulo(),
                request.getMensaje(),
                request.getTipo(),
                request.getUsuarioId(),
                request.getMetadata()
        );

        NotificacionEntity saved = notificacionRepository.save(notificacion);
        log.info("Notificacion creada con ID: {}", saved.getId());

        return convertToDto(saved);
    }

    /**
     * Marca una notificación específica como leída.
     *
     * @param id ID de la notificación.
     * @param usuarioId ID del usuario (para validación de propiedad).
     */
    @Transactional
    public void marcarComoLeida(Long id, Long usuarioId) {
        log.info("Marcando notificacion {} como leida para usuario: {}", id, usuarioId);
        notificacionRepository.marcarComoLeida(id, usuarioId);
    }

    /**
     * Marca todas las notificaciones de un usuario como leídas.
     *
     * @param usuarioId ID del usuario.
     */
    @Transactional
    public void marcarTodasComoLeidas(Long usuarioId) {
        log.info("Marcando todas las notificaciones como leidas para usuario: {}", usuarioId);
        notificacionRepository.marcarTodasComoLeidas(usuarioId);
    }

    /**
     * Elimina una notificación específica de un usuario.
     *
     * @param id ID de la notificación.
     * @param usuarioId ID del usuario (para validación de propiedad).
     */
    @Transactional
    public void eliminarNotificacion(Long id, Long usuarioId) {
        log.info("Eliminando notificacion {} para usuario: {}", id, usuarioId);
        notificacionRepository.deleteByIdAndUsuarioId(id, usuarioId);
    }

    /**
     * Procesa un evento de notificación y crea la notificación correspondiente.
     * Método utilizado por el consumidor de eventos de RabbitMQ.
     *
     * @param titulo Título de la notificación.
     * @param mensaje Mensaje detallado.
     * @param tipo Tipo de notificación.
     * @param usuarioId ID del usuario destinatario.
     * @param metadata Metadatos adicionales.
     */
    @Transactional
    public void procesarEventoNotificacion(String titulo, String mensaje, String tipo, Long usuarioId, String metadata) {
        log.info("Procesando evento de notificacion - Tipo: {}, Usuario: {}", tipo, usuarioId);

        CrearNotificacionRequest request = new CrearNotificacionRequest();
        request.setTitulo(titulo);
        request.setMensaje(mensaje);
        request.setTipo(tipo);
        request.setUsuarioId(usuarioId);
        request.setMetadata(metadata);

        crearNotificacion(request);
    }

    /**
     * Convierte una entidad NotificacionEntity a NotificacionDto.
     *
     * @param entity Entidad a convertir.
     * @return DTO convertido.
     */
    private NotificacionDto convertToDto(NotificacionEntity entity) {
        return modelMapper.map(entity, NotificacionDto.class);
    }
}