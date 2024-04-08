package scrapper.mq.config;


import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfiguration {

    @Value("#{@queueNameForMQ}")
    private String QUEUE_NAME;

    @Value("#{@topicNameForMQ}")
    private String TOPIC_EXCHANGE_NAME;

    @Bean
    public DirectExchange directExchange() {
        return new DirectExchange(TOPIC_EXCHANGE_NAME);
    }

    @Bean
    public Queue queue() {
        return new Queue(QUEUE_NAME, false);
    }

    @Bean
    public Binding binding() {
        return BindingBuilder.bind(queue()).to(directExchange()).with(QUEUE_NAME);
    }

    @Bean
    public MessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}
