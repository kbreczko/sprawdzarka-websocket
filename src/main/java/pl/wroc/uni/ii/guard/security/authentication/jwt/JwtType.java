package pl.wroc.uni.ii.guard.security.authentication.jwt;

import java.util.concurrent.TimeUnit;

/**
 * Enum with available jwt token
 */


public enum JwtType {
    ACCESS_TOKEN(TimeUnit.MINUTES.toMillis(15));

    private final long expirationTime;

    JwtType(long expirationTime) {
        this.expirationTime = expirationTime;
    }

    public long getExpirationTime() {
        return expirationTime;
    }
}
