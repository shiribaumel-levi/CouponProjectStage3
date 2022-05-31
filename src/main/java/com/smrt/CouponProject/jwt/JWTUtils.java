package com.smrt.CouponProject.jwt;

import com.smrt.CouponProject.beans.UserDetails;
import com.smrt.CouponProject.exceptions.JwtException;
import io.jsonwebtoken.*;
import org.springframework.stereotype.Service;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class JWTUtils {
    // Our signature algorithm
    private String signatureAlgorithm = SignatureAlgorithm.HS256.getJcaName();
    // Our secret key
    private String encodedSecretKey = "AbCdEfGhIjKlMnOpQrStUvWxYz12345678901234567890";
    // the actual key, ciphered with the signature algorithm chosen above.
    private Key decodedSecretKey = new SecretKeySpec(Base64.getDecoder().decode(encodedSecretKey), this.signatureAlgorithm);

    /**
     * returns a JWT from userDetails.
     * @param userDetails ID, password, email, and role.
     * @return JWT signed with our key.
     */
    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("id", userDetails.getId());
        claims.put("role", userDetails.getRole());
        return createToken(claims, userDetails.getEmail());
    }

    /**
     * creates JWT from claims and subject.
     * @param claims desired body of the JWT.
     * @param email desired subject of the JWT.
     * @return JWT signed with our key.
     */
    private String createToken(Map<String, Object> claims, String email) {
        Instant now = Instant.now();
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(email)
                .setIssuedAt(Date.from(now))
                .setExpiration(Date.from(now.plus(30, ChronoUnit.MINUTES)))
                .signWith(this.decodedSecretKey)
                .compact();
    }

    /**
     * returns the claims of a given JWT.
     * @param token JWT.
     * @return JWT's claims.
     * @throws JwtException if JWT is invalid.
     */
    private Claims extractAllClaims(String token) throws JwtException {
        try
        {
            JwtParser jwtParser = Jwts.parserBuilder()
                    .setSigningKey(this.decodedSecretKey)
                    .build();
            return jwtParser.parseClaimsJws(token).getBody();
        }
        catch (Exception e) {
            throw new JwtException("invalid token");
        }
    }

    /**
     * get email from JWT
     * @param token JWT
     * @return client's email.
     * @throws JwtException if JWT is invalid.
     */
    private String extractEmail(String token) throws JwtException{
        return extractAllClaims(token).getSubject();
    }

    /**
     * get expiration date from JWT.
     * @param token JWT.
     * @return expiration of JWT.
     * @throws JwtException if JWT is invalid.
     */
    private Date extractExpiration(String token) throws JwtException {
        return extractAllClaims(token).getExpiration();
    }



    /**
     * validates JWT, and returns userDetails.
     * @param token a JWT
     * @return the userDetails inside the JWT, if it was valid.
     * @throws JwtException if JWT is invalid.
     */
    public UserDetails validateToken(String token) throws JwtException {

        Claims claims = extractAllClaims(token);
        return new UserDetails(claims.getSubject(), "*******", claims.get("role", String.class), claims.get("id", Integer.class));
    }
}