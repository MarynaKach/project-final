package com.javarush.jira.login.internal.jwt;
/*

import com.javarush.jira.login.AuthUser;
import com.javarush.jira.login.User;
import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.Charset;
import java.security.Key;
import java.util.Date;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtUtils {
    @Value("${jwt.secret}")
    private String jwtSecret;
    @Value("${jwt.expiration}")
    private int jwtExpirationMs;
    private byte[] apiKeySecretBytes;

    private void init() {
        apiKeySecretBytes = getApiKeySecretBytes();
    }

    private byte[] getApiKeySecretBytes() {
        if (jwtSecret == null) {
            throw new IllegalArgumentException("JWT secret is not set");
        }
        return jwtSecret.getBytes(Charset.defaultCharset());
    }

    public String generateJwtToken() {
        AuthUser authUser = AuthUser.safeGet();
        User user = authUser.getUser();
        Key signingKey = new SecretKeySpec(apiKeySecretBytes,
                SignatureAlgorithm.HS512.getJcaName());
        return Jwts.builder()
                .setSubject(user.getEmail())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpirationMs))
                .signWith(signingKey)
                .compact();
    }

    public String getUserEmailFromJwtToken(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(apiKeySecretBytes)
                .build()
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject();
    }

    public boolean validateJwtToken(String authToken) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(apiKeySecretBytes)
                    .build()
                    .parseClaimsJws(authToken);
            return true;
        } catch (ExpiredJwtException e) {
            // Обработка исключения истекшего токена
            return false;
        } catch (JwtException e) {
            // Обработка остальных исключений
            return false;
        }
    }
}
*/
