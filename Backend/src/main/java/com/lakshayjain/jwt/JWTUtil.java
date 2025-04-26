package com.lakshayjain.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class JWTUtil {

    private static final String SECRET_KEY =
            "developer_2025_developer_2025_developer_2025_developer_2025_developer_2025_developer_2025";

    public String issueToken(String subject){
        return issueToken(subject,Map.of());
    }

    public String issueToken(String subject, String ...scopes){
        return issueToken(subject,Map.of("scopes", scopes));
    }

    public String issueToken(String subject, List<String> scopes){
        return issueToken(subject,Map.of("scopes", scopes));
    }

    public String issueToken(
            String subject,
            Map<String, Object> claims){
        String secretToken = Jwts
                .builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuer("https://lakshay.com")
                .setIssuedAt(Date.from(Instant.now()))
                .setExpiration(Date.from(Instant.now().plus(15, ChronoUnit.DAYS)))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();

        return secretToken;
    }

    public String getSubject(String token){
        return getClaims(token).getSubject();
    }

    private Claims getClaims(String token) {
        Claims claims = Jwts
                .parser()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
        return claims;
    }

    private Key getSigningKey() {
        return Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
    }

    public boolean isTokenValid(String jwt, String username){
        String subject = getSubject(jwt);
        return subject.equals(username) && !isTokenExpired(jwt);
    }

    private boolean isTokenExpired(String jwt) {
        Date today = Date.from(Instant.now());
        return getClaims(jwt).getExpiration().before(today);
    }

}