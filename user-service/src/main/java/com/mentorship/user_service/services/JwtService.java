package com.mentorship.user_service.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import java.util.function.Function;
import java.security.Key;
import java.util.HashMap;
import java.util.Map;

@Service
public class JwtService {
    @Value("${security.jwt.secret-key}")
    private String secretKey;
    @Value("${security.jwt.expiration}")
    private long jwtExpirationInMs;

    public String extractUsername(String token) {
        // Logic to extract username from JWT token
        return extractClaim(token, Claims::getSubject);
    };

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }
    public Key getSignInKey() {
        byte[] keyBytes = java.util.Base64.getDecoder().decode(secretKey);
        return io.jsonwebtoken.security.Keys.hmacShaKeyFor(keyBytes);
    }
    private Claims extractAllClaims(String token) {
        try {
            return  io.jsonwebtoken.Jwts
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
        return claims.getExpiration().before(new java.util.Date());
    }
    public String generateToken(Map<String,Object> extraClaims, UserDetails userDetails) {
        return buildToken(new java.util.HashMap<>(), userDetails, jwtExpirationInMs);
    }

    private String buildToken(HashMap hashMap, UserDetails userDetails, long jwtExpirationInMs) {
        // TODO Auto-generated method stub

        return io.jsonwebtoken.Jwts.builder()
            .setClaims(hashMap)
            .setSubject(userDetails.getUsername())
            .setIssuedAt(new java.util.Date(System.currentTimeMillis()))
            .setExpiration(new java.util.Date(System.currentTimeMillis() + jwtExpirationInMs))
            .signWith(getSignInKey(), io.jsonwebtoken.SignatureAlgorithm.HS256)
            .compact();}
}
