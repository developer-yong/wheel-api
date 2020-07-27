package com.api.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JwtUtils {

    /**
     * 生成用户token
     *
     * @param secret     秘钥
     * @param expiration 过期时间
     *                   <p>秒</P>
     * @param userInfo   用户信息
     * @return token
     */
    public static String createToken(String secret, long expiration, Map<String, String> userInfo) {
        Map<String, Object> map = new HashMap<>();
        map.put("alg", "HS256");
        map.put("typ", "JWT");
        JWTCreator.Builder builder = JWT.create().withHeader(map);

        for (String key : userInfo.keySet()) {
            builder.withClaim(key, userInfo.get(key));
        }
        return builder.withExpiresAt(new Date(System.currentTimeMillis() + expiration * 1000)) //超时设置,设置过期的日期
                .withIssuedAt(new Date()) //签发时间
                .sign(Algorithm.HMAC256(secret)); //SECRET加密;
    }

    /**
     * 校验token并解析token
     *
     * @param secret 秘钥
     * @param token  token
     * @return 用户信息
     */
    public static Map<String, Claim> verifyToken(String secret, String token) {
        DecodedJWT jwt;
        try {
            JWTVerifier verifier = JWT.require(Algorithm.HMAC256(secret)).build();
            jwt = verifier.verify(token);
        } catch (Exception e) {
            return null;
        }
        return jwt.getClaims();
    }

}
