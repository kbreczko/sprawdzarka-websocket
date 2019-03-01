package pl.wroc.uni.ii.guard.security.authentication.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.impl.DefaultClock;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.function.Function;

/**
 * Component for validating JWT tokens
 */

@Component
@Slf4j
public class JwtValidator {
    private final Clock clock = DefaultClock.INSTANCE;

    @Value("${app.security.jwt.secret}")
    private String secret;

    public boolean validateToken(String token, JwtType jwtType) {
        Function<Claims, String> getTypeFromToken = (claims) -> (String) claims.get(JwtProperties.TOKEN_TYPE_NAME);
        String jwtTokenName = getClaimFromToken(token, getTypeFromToken).orElse(null);
        return getEmailFromToken(token).isPresent() && !isTokenExpired(token) && jwtType.name().equals(jwtTokenName);
    }

    public Optional<String> getEmailFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    private boolean isTokenExpired(String token) {
        return getClaimFromToken(token, Claims::getExpiration).map(date -> date.before(clock.now())).orElse(true);
    }

    private <T> Optional<T> getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        try {
            final Claims claims = getAllClaimsFromToken(token);
            return Optional.ofNullable(claimsResolver.apply(claims));
        } catch (IllegalArgumentException e) {
            log.error("An error occurred during getting claim from token", e);
        } catch (ExpiredJwtException e) {
            log.warn("The token is expired and not valid anymore", e);
        } catch (JwtException e) {
            log.warn("An error occurred while parsing", e);
        }

        return Optional.empty();
    }

    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parser()
            .setSigningKey(secret)
            .parseClaimsJws(token)
            .getBody();
    }

    public String getTokenFromHeader(String tokenHeader) {
        return tokenHeader.replace(JwtProperties.TOKEN_SCHEME, "");
    }

    public boolean hasToken(String tokenHeader) {
        return tokenHeader != null && tokenHeader.startsWith(JwtProperties.TOKEN_SCHEME);
    }
}
