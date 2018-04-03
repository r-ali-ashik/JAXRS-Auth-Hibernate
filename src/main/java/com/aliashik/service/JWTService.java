package com.aliashik.service;

import io.jsonwebtoken.Claims;

import java.security.Key;

public interface JWTService {
    String createJWT(Integer userId, String username,  String subject, long ttlMillis);
    Claims parseToken(String token);
    Key generateKey();
}
