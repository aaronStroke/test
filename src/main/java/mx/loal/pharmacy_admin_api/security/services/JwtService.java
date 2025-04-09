package mx.loal.pharmacy_admin_api.security.services;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import mx.loal.pharmacy_admin_api.exceptions.ExpiredTokenException;
import mx.loal.pharmacy_admin_api.exceptions.InvalidTokenException;
import mx.loal.pharmacy_admin_api.utils.constants.AuthConstants;
import mx.loal.pharmacy_admin_api.utils.constants.ExceptionMessageConstants;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Slf4j
@Service
public class JwtService {

    @Value("${application.security.jwt.secret-key}")
    private String secretKey;
    @Value("${application.security.jwt.expiration}")
    private long jwtExpiration;
    @Value("${application.security.jwt.refresh-token.expiration}")
    private long refreshExpiration;

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final var claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        claims
            .put(AuthConstants.ROLE_CLAIM, userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .findFirst().orElseThrow(() -> {
                    log.error(ExceptionMessageConstants.ROLE_NOT_FOUND_MSG);
                    return new RuntimeException(ExceptionMessageConstants.ROLE_NOT_FOUND_MSG);
                })
            );

        return generateToken(claims, userDetails);
    }

    public String generateToken(
        Map<String, Object> extraClaims,
        UserDetails userDetails
    ) {
        return buildToken(extraClaims, userDetails, jwtExpiration);
    }

    public String generateRefreshToken(
        UserDetails userDetails
    ) {
        return buildToken(new HashMap<>(), userDetails, refreshExpiration);
    }

    @SneakyThrows
    private String buildToken(
        Map<String, Object> extraClaims,
        UserDetails userDetails,
        long expiration
    ) {
        log.info("Building token for user {}", userDetails.getUsername());
        var today = new Date();
        var expirationDate = Date.from(today.toInstant().plusMillis(expiration));
        return Jwts
            .builder()
            .subject(userDetails.getUsername())
            .claims(extraClaims)
            .issuedAt(today)
            .expiration(expirationDate)
            .signWith(getSignInKey())
            .compact();
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        boolean isValid = (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
        log.info("Token validation for user {}: {}", username, isValid);
        return isValid;
    }

    private boolean isTokenExpired(String token) {
        var expiration = extractExpiration(token);
        return expiration.before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    private Claims extractAllClaims(String token) {
        try {
            return Jwts
                .parser()
                .verifyWith(getSignInKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
        } catch (ExpiredJwtException e) {
            log.warn("Expired token: {}", e.getMessage());
            throw new ExpiredTokenException(ExceptionMessageConstants.EXPIRED_TOKEN_MSG);
        } catch (IllegalArgumentException e) {
            log.warn("Invalid token: {}", e.getMessage());
            throw new InvalidTokenException(ExceptionMessageConstants.INVALID_TOKEN_MSG);
        }
    }

    private SecretKey getSignInKey() {
        var bytes = Base64.getDecoder()
                .decode(secretKey.getBytes(StandardCharsets.UTF_8));
        return Keys.hmacShaKeyFor(bytes);
    }
}
