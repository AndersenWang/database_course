package org.example.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

@Component
public class JwtUtil {
    //Jwt秘钥
    @Value("${spring.security.jwt.key}")
    private String key;
    @Value("${spring.security.jwt.expire}")
    private int expire;

    public boolean invalidateJwt(String token){
        String str = this.convertToken(token);
        if (str == null) return false;
        Algorithm algorithm = Algorithm.HMAC256(key);
        JWTVerifier jwtVerifier = JWT.require(algorithm).build();
        try {
            DecodedJWT jwt = jwtVerifier.verify(str);
            String id = jwt.getId();
            return true;
        }catch (JWTVerificationException e){
            return false;
        }
    }
    public DecodedJWT resolveJwt(String token){
        String str = this.convertToken(token);
        if (str == null) return null;
        Algorithm algorithm = Algorithm.HMAC256(key);
        JWTVerifier jwtVerifier = JWT.require(algorithm).build();
        try {
            DecodedJWT verify = jwtVerifier.verify(str);
            Date expireAt = verify.getExpiresAt();
            return new Date().after(expireAt) ? null : verify;
        }catch (JWTVerificationException e){
            return null;
        }
    }
    private String convertToken(String token){
        if (token == null || !token.startsWith("Bearer ")) return null;
        return token.substring("Bearer ".length());
    }

    public String createJwt(String username, String role){
        Algorithm algorithm = Algorithm.HMAC256(key);
        Date expireTime = getExpireTime();
        return JWT.create()
                .withJWTId(UUID.randomUUID().toString())
                .withClaim("username", username)
                .withClaim("role", role)
                .withExpiresAt(expireTime)
                .withIssuedAt(new Date())
                .sign(algorithm);
    }
    public Date getExpireTime(){
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.SECOND, 3600 * 24 * expire);
        return calendar.getTime();
    }

    public UserDetails toUser(DecodedJWT jwt){
        Map<String, Claim> claims = jwt.getClaims();
        return User
                .withUsername(claims.get("username").asString())
                .password("*******")
                .build();
    }
    public String toUsername(DecodedJWT jwt){
        Map<String, Claim> claims = jwt.getClaims();
        return claims.get("username").asString();
    }
    public String toRole(DecodedJWT jwt){
        Map<String, Claim> claims = jwt.getClaims();
        return claims.get("role").asString();
    }
}
