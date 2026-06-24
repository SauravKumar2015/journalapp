package com.saurav.journalApp.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtUtil {

    // 🔐 256-bit secret key (DO NOT shorten this)
    private static final String SECRET_KEY =
            "TaK+HaV^uvCHEFsEVfypW#7g9^k*Z8$VTaK+HaV^uvCHEFsEVfypW";

    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
    }

    // ✅ Extract username
    public String extractUsername(String token) {
        Claims claims = extractAllClaims(token);
        return claims.getSubject();
    }

    // ✅ Extract expiration
    public Date extractExpiration(String token) {
        return extractAllClaims(token).getExpiration();
    }

    // ✅ Parse & verify token (signature verified here)
    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    // ✅ Expiry check
    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    // ✅ Token generation (same as yours)
    public String generateToken(String username) {
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, username);
    }

    private String createToken(Map<String, Object> claims, String subject) {
        return Jwts.builder()
                .claims(claims)
                .subject(subject)
                .header().empty().add("typ", "JWT")
                .and()
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(
                        new Date(System.currentTimeMillis() + 1000 * 60 * 60)
                ) // 1 hour
                .signWith(getSigningKey())
                .compact();
    }

    // ✅ FULL validation (signature + expiry)
    public boolean validateToken(String token) {
        try {
            extractAllClaims(token); // verifies signature
            return !isTokenExpired(token);
        } catch (Exception e) {
            return false;
        }
    }
}
