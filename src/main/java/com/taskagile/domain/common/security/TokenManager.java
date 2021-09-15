package com.taskagile.domain.common.security;

import com.taskagile.domain.model.user.UserId;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;

@Component
public class TokenManager {

    private static final Logger log = LoggerFactory.getLogger(TokenManager.class);
    private Key secretKey;

    public TokenManager(@Value("${app.token-secret-key}") String secretKey) {
        this.secretKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretKey));
    }

    public String jwt(UserId userId) {
        return Jwts.builder().setSubject(String.valueOf(userId.value())).signWith(secretKey).compact();
    }

    public UserId verifyJwt(String jws) {
        String tokenValue = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(jws).getBody().getSubject();
        return new UserId(Long.parseLong(tokenValue));
    }
}
