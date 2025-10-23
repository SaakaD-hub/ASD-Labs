package com.ads.apigateway.utl;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureException;
import org.springframework.stereotype.Component;

@Component
public class JwtUtil {

    private static final String SECRET_KEY = "MySuperSecretKeyForJWTSigning123"; // must match your Office Manager Service

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(SECRET_KEY.getBytes())
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (SignatureException ex) {
            System.out.println("Invalid JWT signature");
        } catch (Exception ex) {
            System.out.println("Invalid JWT token: " + ex.getMessage());
        }
        return false;
    }

    public String extractUsername(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(SECRET_KEY.getBytes())
                .build()
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject();
    }
}
