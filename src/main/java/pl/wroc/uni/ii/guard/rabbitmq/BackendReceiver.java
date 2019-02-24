package pl.wroc.uni.ii.guard.rabbitmq;


import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import pl.wroc.uni.ii.guard.models.dtos.BackendMessage;

import static pl.wroc.uni.ii.guard.configurations.QueueConfig.QUEUE_TO_RECEIVE_RESULT;

@Component
@Slf4j
public class BackendReceiver {

    @RabbitListener(queues = QUEUE_TO_RECEIVE_RESULT)
    @Transactional
    public void receive(BackendMessage backendMessage) {
        log.info("Received data from queue" + QUEUE_TO_RECEIVE_RESULT + " data: " + backendMessage);
        log.warn("{}", backendMessage);
    }
}
