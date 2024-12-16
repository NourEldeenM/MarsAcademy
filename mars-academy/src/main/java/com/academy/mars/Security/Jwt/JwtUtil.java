//package com.academy.mars.Security;
//
//import io.jsonwebtoken.*;
//import io.jsonwebtoken.security.Keys;
//import io.jsonwebtoken.Jwts;
//import org.springframework.stereotype.Component;
//
//import java.security.Key;
//import java.util.Date;
//import java.util.Map;
//
//@Component
//public class JwtUtil {
//
//    private static final String SECRET = "your-256-bit-secret-key-goes-here"; // Use at least a 256-bit key
//    private static final long EXPIRATION_TIME = 86400000; // 24 hours in milliseconds
//
//    private final Key key = Keys.hmacShaKeyFor(SECRET.getBytes());
//
//    // Generate a JWT
//    public String generateToken(String username, Map<String, Object> claims) {
//        return Jwts.builder()
//                .setClaims(claims)
//                .setSubject(username)
//                .setIssuedAt(new Date())
//                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
//                .signWith(key, SignatureAlgorithm.HS256)
//                .compact();
//    }
//
//    // Validate a JWT
//    public boolean validateToken(String token) {
//        try {
//            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
//            return true;
//        } catch (JwtException | IllegalArgumentException e) {
//            return false;
//        }
//    }
//
//    // Extract claims
//    public Claims extractClaims(String token) {
//        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
//    }
//
//}