package com.cultureconnect.apigateway.config;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtTokenUtil {

    @Value("${jwt.secret}")
    private String secret;

    // ✅ Extract all claims (used by Gateway)
    public Claims getAllClaimsFromToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(Keys.hmacShaKeyFor(secret.getBytes()))
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    // ✅ Validate token expiry
    public boolean isTokenValid(String token) {
        try {
            Claims claims = getAllClaimsFromToken(token);
            return claims.getExpiration() != null
                    && claims.getExpiration().after(new Date());
        } catch (Exception e) {
            return false; // invalid or tampered token
        }
    }

    // ✅ Convenience methods (important for role-based access)

    public String getUsername(String token) {
        return getAllClaimsFromToken(token).getSubject();
    }

    public String getRole(String token) {
        return getAllClaimsFromToken(token).get("role", String.class);
    }

    public Long getUserId(String token) {
        return getAllClaimsFromToken(token).get("userId", Long.class);
    }
}

