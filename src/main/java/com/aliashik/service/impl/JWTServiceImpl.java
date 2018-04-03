package com.aliashik.service.impl;

import com.aliashik.service.JWTService;
import com.aliashik.service.UserService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class JWTServiceImpl implements JWTService{

    @Autowired
    private Logger logger;

    @Autowired
    private UserService userService;

    public  String createJWT(Integer userId, String username,  String subject, long ttlMillis) {
        logger.info(":::: Initiating token creation ::::");
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);
        Key key = generateKey();

        Map userRoles = new HashMap();
        userRoles.put("Roles", userService.getUserRolesByUserID(userId));

        JwtBuilder builder = Jwts.builder().setId(username)
                .setIssuedAt(now)
                .addClaims(userRoles)
                .setSubject(subject)
                .setIssuer("_"+userId+"_")
                .signWith(signatureAlgorithm, key);

        if (ttlMillis >= 0) {
            long expMillis = nowMillis + ttlMillis;
            Date exp = new Date(expMillis);
            builder.setExpiration(exp);
        }
        return builder.compact();
    }

    public  Claims parseToken(String token) {
        return Jwts.parser()
                .setSigningKey(generateKey())
                .parseClaimsJws(token)
                .getBody();
    }

    public  Key generateKey() {
        String keyString = "aliashik";
        Key key = new SecretKeySpec(keyString.getBytes(), 0, keyString.getBytes().length, "DES");
        return key;
    }
}
