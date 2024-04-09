package scrapper.mq.config;


import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfiguration {

    @Value("#{@queueNameForMQ}")
    private String QUEUE_NAME;

    @Value("#{@queueNameForMQ_dlq}")
    private String QUEUE_NAME_FOR_DLQ;

    @Value("#{@topicNameForMQ}")
    private String TOPIC_EXCHANGE_NAME;

    @Value("#{@topicNameForMQ_dlq}")
    private String TOPIC_EXCHANGE_NAME_FOR_DLQ;

    @Bean
    public DirectExchange directExchange() {
        return new DirectExchange(TOPIC_EXCHANGE_NAME);
    }

    @Bean
    public DirectExchange directExchangeForDlq() {
        return new DirectExchange(TOPIC_EXCHANGE_NAME_FOR_DLQ);
    }

    @Bean
    public Queue queue() {
        return QueueBuilder.durable(QUEUE_NAME)
                .withArgument("x-dead-letter-exchange", TOPIC_EXCHANGE_NAME_FOR_DLQ)
                .withArgument("x-dead-letter-routing-key", QUEUE_NAME_FOR_DLQ)
                .build();
    }

    @Bean
    public Queue deadLetterQueue() {
        return QueueBuilder.durable(QUEUE_NAME_FOR_DLQ).build();
    }

    @Bean
    public Binding binding() {
        return BindingBuilder.bind(queue()).to(directExchange()).with(QUEUE_NAME);
    }

    @Bean
    public Binding bindingForDlq() {
        return BindingBuilder.bind(deadLetterQueue()).to(directExchangeForDlq()).with(QUEUE_NAME_FOR_DLQ);
    }
}
