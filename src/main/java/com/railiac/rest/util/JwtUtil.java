package com.railiac.rest.util;

import com.railiac.rest.database.model.User;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Set;

@Service
public class JwtUtil {

    @Value("${railiac.jwt.secret}")
    private String jwtSecret;

    @Value("${railiac.jwt.duration.long-term}")
    private long longTermokenDurationMs;

    @Value("${railiac.jwt.duration.default}")
    private long defaultTokenDurationMs;

    public String generate(User user, boolean rememberMe) {
        return Jwts
                .builder()
                .setId(user.getEmail())
                .setSubject(user.getName())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setClaims(new HashMap<>() {
                    {
                        put("user", user);
                        put("roles", user.getRoles());
                    }
                })
                .setExpiration(new Date(System.currentTimeMillis() + (rememberMe ? this.longTermokenDurationMs : this.defaultTokenDurationMs)))
                .signWith(SignatureAlgorithm.HS512,
                        this.jwtSecret.getBytes()).compact();
    }

    public Claims validate(String jwt) throws ExpiredJwtException, UnsupportedJwtException, MalformedJwtException, SignatureException, IllegalArgumentException {
        return Jwts.parser().setSigningKey(this.jwtSecret.getBytes()).parseClaimsJws(jwt).getBody();
    }
}
