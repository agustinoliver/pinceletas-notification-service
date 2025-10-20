package ar.edu.utn.frc.tup.tesis.pinceletas_notification_service.configs;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuración de RabbitMQ para el sistema de notificaciones.
 * Define colas, exchanges y bindings para la comunicación asíncrona de eventos.
 */
@Configuration
public class RabbitMQConfig {

    /** Nombre de la cola para notificaciones. */
    public static final String QUEUE = "notificaciones.queue";
    /** Nombre del exchange para notificaciones. */
    public static final String EXCHANGE = "notificaciones.exchange";
    /** Routing key para enrutamiento de mensajes. */
    public static final String ROUTING_KEY = "notificaciones.key";

    /**
     * Configura la cola durable para notificaciones.
     *
     * @return Queue configurada y durable.
     */
    @Bean
    public Queue queue() {
        return new Queue(QUEUE, true);
    }

    /**
     * Configura el exchange de tipo Topic para notificaciones.
     *
     * @return TopicExchange para enrutamiento flexible.
     */
    @Bean
    public TopicExchange exchange() {
        return new TopicExchange(EXCHANGE);
    }

    /**
     * Establece el binding entre la cola y el exchange con la routing key.
     *
     * @param queue Cola a enlazar.
     * @param exchange Exchange destino.
     * @return Binding configurado.
     */
    @Bean
    public Binding binding(Queue queue, TopicExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(ROUTING_KEY);
    }

    /**
     * Configura el convertidor de mensajes a JSON.
     *
     * @return MessageConverter para serialización JSON.
     */
    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    /**
     * Configura RabbitTemplate con convertidor JSON.
     *
     * @param connectionFactory Factory de conexiones RabbitMQ.
     * @return RabbitTemplate configurado.
     */
    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(jsonMessageConverter());
        return rabbitTemplate;
    }
}