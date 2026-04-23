package com.example.demo.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {

    @Value("${app.jwt.secret:}")
    private String jwtSecret;

    @Value("${app.jwt.expiration:3600000}")
    private Long jwtExpiration;

    public String resolveSubject(String token) {
        return parseClaim(token, Claims::getSubject);
    }

    public <T> T parseClaim(String token, Function<Claims, T> resolver) {
        return resolver.apply(parsePayload(token));
    }

    public String issueToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("role", userDetails.getAuthorities().stream().findFirst().orElseThrow().getAuthority());
        return assembleToken(claims, userDetails);
    }

    public boolean verifyToken(String token, UserDetails userDetails) {
        String subject = resolveSubject(token);
        return subject.equals(userDetails.getUsername()) && !hasTokenExpired(token);
    }

    public long getTokenLifetime() {
        return jwtExpiration;
    }

    private String assembleToken(Map<String, Object> extraClaims, UserDetails userDetails) {
        Date now = new Date();
        return Jwts.builder()
                .claims(extraClaims)
                .subject(userDetails.getUsername())
                .issuedAt(now)
                .expiration(new Date(now.getTime() + jwtExpiration))
                .signWith(resolveSecretKey())
                .compact();
    }

    private boolean hasTokenExpired(String token) {
        return parseClaim(token, Claims::getExpiration).before(new Date());
    }

    private Claims parsePayload(String token) {
        return Jwts.parser()
                .verifyWith(resolveSecretKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    private SecretKey resolveSecretKey() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
    }
}
