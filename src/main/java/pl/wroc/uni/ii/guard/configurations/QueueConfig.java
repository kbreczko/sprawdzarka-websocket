package pl.wroc.uni.ii.guard.configurations;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class QueueConfig {
    public final static String QUEUE_TO_RECEIVE_RESULT = "guardQueue";

    @Bean
    Queue receiveQueue() {
        return new Queue(QUEUE_TO_RECEIVE_RESULT);
    }

    @Bean
    public Jackson2JsonMessageConverter producerJackson2MessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

}
