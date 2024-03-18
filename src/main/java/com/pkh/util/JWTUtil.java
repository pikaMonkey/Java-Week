package com.pkh.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.pkh.dao.po.User;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.springframework.util.ObjectUtils;

import java.util.Date;

@Slf4j
public class JWTUtil {
    // 过期时间（分钟）
    private static final long EXPIRE_TIME = 6*60 * 60 * 1000;

    public static final long REDIS_TIME = 6*3600L;

    /**
     * 校验token是否正确
     *
     * @param token    密钥
     * @param username 登录名
     * @param password 密码
     * @return
     */
    public static boolean verify(String token, String username, String password) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(password);

            JWTVerifier verifier = JWT.require(algorithm).withClaim("username", username).build();

            DecodedJWT jwt = verifier.verify(token);

            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 获取登录名
     *
     * @param token
     * @return
     */
    public static String getUsername(String token) {
        try {
            DecodedJWT jwt = JWT.decode(token);

            return jwt.getClaim("username").asString();
        } catch (JWTDecodeException e) {
            return null;
        }
    }

    /**
     * 生成签名
     *
     * @param username
     * @param password
     * @return
     */
    public static String sign(String username, String password) {
        // 指定过期时间
        Date date = new Date(System.currentTimeMillis() + EXPIRE_TIME);

        Algorithm algorithm = Algorithm.HMAC256(password);

        String token = JWT.create()
                .withClaim("username", username)
                .withExpiresAt(date)
                .sign(algorithm);
        log.info("login-data:{}token:{}",date,token);

        return token;
    }

    /**
     * 获取当前登录名
     *
     * @return
     */
    public static String getLoginUserName() {
        try {
            User user = (User) SecurityUtils.getSubject().getPrincipal();
            if(ObjectUtils.isEmpty(user)){
                return null;
            }
            return user.getUserName();
        } catch (JWTDecodeException e) {
            return null;
        }
    }
}
