package pl.wroc.uni.ii.guard.security.authentication.interceptors;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Component;
import pl.wroc.uni.ii.guard.security.authentication.services.WebSocketAuthenticatorService;

@Component
@Slf4j
public class WebSocketInterceptor implements ChannelInterceptor {
    private final WebSocketAuthenticatorService webSocketAuthenticatorService;

    @Autowired
    public WebSocketInterceptor(WebSocketAuthenticatorService webSocketAuthenticatorService) {
        this.webSocketAuthenticatorService = webSocketAuthenticatorService;
    }

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);

        if (accessor != null && accessor.getCommand() == StompCommand.CONNECT) {
            final String authorizationHeader = accessor.getFirstNativeHeader(HttpHeaders.AUTHORIZATION);
            UsernamePasswordAuthenticationToken user = webSocketAuthenticatorService.getAuthenticatedOrFail(authorizationHeader);
            log.info("User has been authorized with the e-mail address '{}'", user.getName());
            accessor.setUser(user);
        }

        return message;
    }
}
