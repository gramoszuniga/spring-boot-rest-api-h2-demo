package com.einfari.springbootrestapih2demo.common.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;

/**
 * @author : Gonzalo Ramos Zúñiga
 * @since : 2022-10-10
 **/
@Slf4j
@Component
public class JwtUtils {

    private static final int EXPIRATION_MS = 3600000;
    private static final SecretKey SECRET_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS512);
    private static final JwtParser JWT_PARSER = Jwts.parserBuilder().setSigningKey(SECRET_KEY).build();
    private static final String TOKEN_EXPIRED = "Token is expired.";
    private static final String TOKEN_IS_REQUIRED = "Token is required.";
    private static final String TOKEN_INVALID = "Token is invalid.";
    private static final String TOKEN_NOT_SUPPORTED = "Token is not supported.";
    private static final String SIGNATURE_INVALID = "Token signature is invalid.";

    public String generateJwtToken(String username) {
        long currentTimeMillis = System.currentTimeMillis();
        return Jwts.builder().addClaims(new HashMap<>()).setSubject(username).setIssuedAt(new Date(currentTimeMillis))
                .setExpiration(new Date(currentTimeMillis + EXPIRATION_MS)).signWith(SECRET_KEY).compact();
    }

    public Boolean validateJwtToken(String token) {
        try {
            JWT_PARSER.parseClaimsJws(token);
        } catch (ExpiredJwtException e) {
            log.error(TOKEN_EXPIRED);
        } catch (UnsupportedJwtException e) {
            log.error(TOKEN_NOT_SUPPORTED);
        } catch (MalformedJwtException e) {
            log.error(TOKEN_INVALID);
        } catch (SignatureException e) {
            log.error(SIGNATURE_INVALID);
        } catch (IllegalArgumentException e) {
            log.error(TOKEN_IS_REQUIRED);
        }
        return true;
    }

    public String getSubject(String token) {
        return JWT_PARSER.parseClaimsJws(token).getBody().getSubject();
    }

}