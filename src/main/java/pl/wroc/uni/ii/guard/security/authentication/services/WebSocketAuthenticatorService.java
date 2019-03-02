package pl.wroc.uni.ii.guard.security.authentication.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pl.wroc.uni.ii.guard.security.authentication.jwt.JwtType;
import pl.wroc.uni.ii.guard.security.authentication.jwt.JwtValidator;
import pl.wroc.uni.ii.guard.user.services.UserCredentialsService;

import java.util.Optional;

@Service
public class WebSocketAuthenticatorService {

    private final JwtValidator jwtValidator;
    private final UserCredentialsService userCredentialsService;

    @Autowired
    public WebSocketAuthenticatorService(JwtValidator jwtValidator, UserCredentialsService userCredentialsService) {
        this.jwtValidator = jwtValidator;
        this.userCredentialsService = userCredentialsService;
    }

    public UsernamePasswordAuthenticationToken getAuthenticatedOrFail(final String authorizationHeader) throws AuthenticationException {
        if (!jwtValidator.hasToken(authorizationHeader))
            throw new AuthenticationCredentialsNotFoundException("Authorization header does not have access token");

        String token = jwtValidator.getTokenFromHeader(authorizationHeader);

        if (!jwtValidator.validateToken(token, JwtType.ACCESS_TOKEN))
            throw new AuthenticationCredentialsNotFoundException("Access token not found");

        String email = jwtValidator.getEmailFromToken(token).orElseThrow(() -> new AuthenticationCredentialsNotFoundException("Email address not found"));

        return Optional.of(email)
            .flatMap(userCredentialsService::getUserDetailsOptional)
            .map(user -> new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities()))
            .orElseThrow(() -> new UsernameNotFoundException("User was not found with e-mail " + email));
    }
}