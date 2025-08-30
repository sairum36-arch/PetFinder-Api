package com.PetFinder.PetFinder.securityConfig;
import com.PetFinder.PetFinder.entity.User;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtTokenProvider {
    private final JwtProperties jwtProperties;
    private final Key jwtAccessKey;
    public JwtTokenProvider(JwtProperties jwtProperties) {
        this.jwtProperties = jwtProperties;
        this.jwtAccessKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtProperties.getSecret()));
    }
    public String generateToken(User user) {
        Map<String, Object> extraClaims = new HashMap<>();
        extraClaims.put("userId", user.getId());
        extraClaims.put("role", user.getCredential().getRole().name());
        return Jwts.builder()
                .setClaims(extraClaims)
                .setSubject(user.getCredential().getEmail())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + jwtProperties.getExpirationMs()))
                .signWith(jwtAccessKey, SignatureAlgorithm.HS256)
                .compact();
    }
    public String getUsernameFromToken(String token) {
        return extractClaim(token, Claims::getSubject);
    }
    public Long getUserIdFromToken(String token) {
        return extractClaim(token, claims -> claims.get("userId", Long.class));
    }
    public boolean isTokenValid(String token, UserDetails userDetails) {
        try {
            final String username = getUsernameFromToken(token);
            return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
        } catch (Exception e) {
            return false;
        }
    }
    private boolean isTokenExpired(String token) {
        try {
            return extractExpiration(token).before(new Date());
        } catch(ExpiredJwtException e) {
            return true;
        }
    }
    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }
    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }
    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .setSigningKey(jwtAccessKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}