package uaic.info.csft.userservice.utils;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
@SuppressWarnings("WeakerAccess")
public class JwtUtil {

    @Value("${jwt.secret:eB9aQjm3N0HF6Xqbj6N0c7picYrhLBze}")
    private String secret;

    @Value("${jwt.expire:1209600}")
    private Long expirationTime;

    public Claims getClaimsFromToken(String token) {
        try {
            return Jwts.parser()
                    .setSigningKey(this.secret.getBytes())
                    .parseClaimsJws(token)
                    .getBody();
        } catch (ExpiredJwtException | UnsupportedJwtException | MalformedJwtException | IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid token.");
        }
    }

    private Date getExpirationTime() {
        return new Date(System.currentTimeMillis() + this.expirationTime * 1000);
    }

    public String generateToken(Map<String, Object> claims) {
        Date createdTime = new Date();
        Date expirationTime = this.getExpirationTime();

        byte[] keyBytes = secret.getBytes();
        SecretKey key = Keys.hmacShaKeyFor(keyBytes);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(createdTime)
                .setExpiration(expirationTime)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public void validateToken(String token) {
        try {
            getClaimsFromToken(token);
        } catch (Exception e) {
            throw e;
        }
    }
}
