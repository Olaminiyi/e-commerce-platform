package com.auth_service.config;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Map;
import java.util.function.Function;

@Slf4j
@Service
public class JwtService {

    private static final String SECRET = "cd08700adb9d70cb9313a051aa52c15a2c7d1fa24f49e9dbf94ca33b5c67b9a3";
    Claims claims;
    SecretKey key;
    public  String extractUsername(String token){
        return  extractClaim(token, Claims::getSubject);
    }

    private Claims extractAllclaims(String token){
        try {
            key = Keys.hmacShaKeyFor(SECRET.getBytes(StandardCharsets.UTF_8));
            claims  = Jwts.parser()
                    .verifyWith(key)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
        }
        catch (JwtException e){
            log.info("invalid or expired token: " + e.getMessage());
        }
        return claims;
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver){
        final Claims claims1 = extractAllclaims(token);
        return claimsResolver.apply(claims1);
    }

    public  String generateToken(
            Map<String,Object> extraClaims,
            UserDetails userDetails
    ){
        return Jwts
                .builder()
                .claims(extraClaims)
                .subject(userDetails.getUsername())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 24))
                .signWith(key, Jwts.SIG.HS256)
                .compact();
    }
}
