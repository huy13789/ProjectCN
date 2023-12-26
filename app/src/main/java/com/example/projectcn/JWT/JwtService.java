package com.example.projectcn.JWT;

import java.nio.charset.StandardCharsets;
import java.security.Key;

import javax.crypto.spec.SecretKeySpec;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class JwtService {
    String secretKey = "JWTAuthenticationHIGHsecuredPasswordVVVp1OH7Xasd707";
    SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
    // Tạo signing key mới mỗi lần generate token
    byte[] secretKeyBytes = secretKey.getBytes(StandardCharsets.UTF_8);
    Key signingKey = new SecretKeySpec(secretKeyBytes, signatureAlgorithm.getJcaName());

    public Claims validateToken(String token) {
        Jws<Claims> jwsClaims = Jwts.parser()
                .setSigningKey(signingKey)
                .parseClaimsJws(token);
        Claims claims = jwsClaims.getBody();

        return claims;

    }
}
