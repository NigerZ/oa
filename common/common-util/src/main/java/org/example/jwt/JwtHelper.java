package org.example.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.impl.crypto.JwtSigner;
import org.springframework.util.StringUtils;

import java.util.Date;

public class JwtHelper {

    private static long tokenExpiration = 24 * 60 * 60 * 1000;
    private static String tokenSignKey = "123456";

    public static String createToken(Long userId, String username) {
        return Jwts.builder()
                .setSubject("AUTH-USER")
                .setExpiration(new Date(System.currentTimeMillis() + tokenExpiration))
                .claim("userId", userId)
                .claim("username", username)
                .signWith(SignatureAlgorithm.HS512, tokenSignKey)
                .compact();
    }

    public static Long getUserId(String token) {

        try {
            if (StringUtils.isEmpty(token)) return null;
            Jws<Claims> claimsJws = Jwts.parser().setSigningKey(tokenSignKey).parseClaimsJws(token);
            Claims claims = claimsJws.getBody();
            Integer userId = (Integer) claims.get("userId");
            return userId.longValue();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String getUsername(String token) {
        if (StringUtils.isEmpty(token)) return null;
        try {
            Jws<Claims> claimsJws = Jwts.parser().setSigningKey(tokenSignKey).parseClaimsJws(token);
            Claims claims = claimsJws.getBody();
            String username = (String) claims.get("username");
            return username;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    public static void main(String[] args) {
        String token = JwtHelper.createToken(1L, "zhangsan");
        System.out.println(token);
        Long userId = JwtHelper.getUserId(token);
        System.out.println(userId);
        String username = JwtHelper.getUsername(token);
        System.out.println(username);
    }
}
