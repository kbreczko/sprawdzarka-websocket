package pl.wroc.uni.ii.guard.communication;


import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import pl.wroc.uni.ii.guard.communication.models.dtos.BackendMessage;

import static pl.wroc.uni.ii.guard.configurations.QueueConfig.QUEUE_TO_RECEIVE_RESULT;

@Component
@Slf4j
public class CommunicationRelay {
    private static final String DESTINATION_USER_URL = "/queue/reply";
    private final SimpMessagingTemplate simpMessagingTemplate;

    @Autowired
    public CommunicationRelay(SimpMessagingTemplate simpMessagingTemplate) {
        this.simpMessagingTemplate = simpMessagingTemplate;
    }

    @RabbitListener(queues = QUEUE_TO_RECEIVE_RESULT)
    @Transactional
    public void receive(BackendMessage backendMessage) {
        log.info("Received data from queue {}, data: {}", QUEUE_TO_RECEIVE_RESULT, backendMessage);
        simpMessagingTemplate.convertAndSendToUser(backendMessage.getUserName(), DESTINATION_USER_URL, backendMessage.getTestResultData());
    }
}
