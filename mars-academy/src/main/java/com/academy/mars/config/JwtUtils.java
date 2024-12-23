package com.academy.mars.config;

import com.academy.mars.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtUtils {
    //    @Value("${security.jwt.secret-key}")
    private String secretKey = "RrkShQAQ1UAdO8xpJphKdedvk6cPqcqAFiYUuNwpdTM";

    //    @Value("${security.jwt.expiration-time}")
    private final long jwtExpiration = 36000000;

    public String extractUserID(String token) {
        try {
            return extractClaim(token, Claims::getSubject);
        } catch (JwtException e) {
            throw new JwtException("Token extraction failed: " + e.getMessage());
        }
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    public String generateToken(User userDetails) {
        return generateToken(new HashMap<>(), userDetails);
    }

    public String generateToken(Map<String, Object> extraClaims, User userDetails) {
        return buildToken(extraClaims, userDetails, jwtExpiration);
    }

    public long getExpirationTime() {
        return jwtExpiration;
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String userId = extractClaim(token, Claims::getSubject);
        return userId.equals(String.valueOf(((User) userDetails).getId())) && !isTokenExpired(token);
    }

    private String buildToken(Map<String, Object> extraClaims, User userDetails, long expiration) {
        extraClaims.put("username", userDetails.getUsername());
        extraClaims.put("email", userDetails.getEmail());
        return Jwts.builder()
                .claims(extraClaims)
                .subject(String.valueOf(userDetails.getId()))
                .claim("role",
                        userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList())
                .issuedAt(new Date())
                .expiration(calculateExpirationDate(expiration))
                .signWith(getSignInKey())
                .compact();
    }

    private Date calculateExpirationDate(long expirationTime) {
        return new Date(System.currentTimeMillis() + expirationTime);
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    private Claims extractAllClaims(String token) {
        try {
            return Jwts.parser()
                    .setSigningKey(getSignInKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            throw new JwtException("Invalid token: " + e.getMessage());
        }
    }

    private SecretKey getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}