package com.mentorship.user_service.services;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.Date;
import java.util.function.Function;
import java.security.Key;
import java.util.HashMap;

@Service
public class JwtService {
    @Value("${security.jwt.secret-key}")
    private String secretKey;
    @Value("${security.jwt.expiration}")
    private long jwtExpirationInMs;

    public String extractUsername(String token) {
        // Logic to extract username from JWT token
        return extractClaim(token, Claims::getSubject);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }
    public Key getSignInKey() {
        byte[] keyBytes = Base64.getDecoder().decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }
    private Claims extractAllClaims(String token) {
        try {
            return  Jwts
            .parserBuilder()
                .setSigningKey(getSignInKey())
                .setAllowedClockSkewSeconds(30) //30 saniye ek süre tanıyoruz tokenın geçerliliği için
                .build()
                .parseClaimsJws(token)
                .getBody();
        } catch (ExpiredJwtException e) {
            return e.getClaims();
        }
        catch (JwtException e) {
            return null;
        }
    }
    public Boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
    public Boolean isTokenExpired(String token) {
        final Claims claims = extractAllClaims(token);
        return claims != null && claims.getExpiration().before(new Date());
    }
    public String generateToken(UserDetails userDetails) {
        return buildToken(new HashMap<>(), userDetails, jwtExpirationInMs);
    }

    public long getExpirationInMs() {
        return jwtExpirationInMs;
    }

    private String buildToken(HashMap hashMap, UserDetails userDetails, long jwtExpirationInMs) {
        // TODO Auto-generated method stub

        return Jwts.builder()
            .setClaims(hashMap)
            .setSubject(userDetails.getUsername())
            .setIssuedAt(new Date(System.currentTimeMillis()))
            .setExpiration(new Date(System.currentTimeMillis() + jwtExpirationInMs))
            .signWith(getSignInKey(), SignatureAlgorithm.HS256)
            .compact();}
}
