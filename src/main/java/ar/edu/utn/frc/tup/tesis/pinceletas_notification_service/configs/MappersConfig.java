package ar.edu.utn.frc.tup.tesis.pinceletas_notification_service.configs;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.modelmapper.Conditions;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuración de mapeadores para la aplicación.
 * Define beans para ModelMapper y ObjectMapper con configuraciones específicas.
 */
@Configuration
public class MappersConfig {

    /**
     * Bean principal de ModelMapper para conversiones entre entidades y DTOs.
     * Configuración estándar sin condiciones especiales.
     *
     * @return Instancia configurada de ModelMapper.
     */
    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    /**
     * Bean especializado de ModelMapper para operaciones de fusión (merge).
     * Solo copia propiedades que no son nulas, útil para actualizaciones parciales.
     *
     * @return ModelMapper configurado para ignorar propiedades nulas.
     */
    @Bean("mergerMapper")
    public ModelMapper mergerMapper() {
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setPropertyCondition(Conditions.isNotNull());
        return mapper;
    }

    /**
     * Bean de ObjectMapper para serialización/deserialización JSON.
     * Configurado para soportar Java Time API y evitar timestamps en fechas.
     *
     * @return ObjectMapper configurado para el sistema.
     */
    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        return objectMapper;
    }
}
