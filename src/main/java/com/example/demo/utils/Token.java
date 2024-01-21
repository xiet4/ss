package com.example.demo.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import com.example.demo.entity.User;
import com.example.demo.service.userService;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.Resource;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletRequestAttributeEvent;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Calendar;
import java.util.Date;

public class Token {
    private static userService staticUserService;
    @Resource
    private userService userService;
    @PostConstruct
    public  void setUserService(){
        staticUserService=userService;
    }

    /**
     * 过期时间5分钟
     */
    private static final long EXPIRE_TIME = 5 * 60 * 1000;
    public static String getToken(String userID,String sign) {
        try {
            Date date = new Date(System.currentTimeMillis() + EXPIRE_TIME);
            Algorithm algorithm = Algorithm.HMAC256(sign);
            return JWT.create()
                    // 将 userId 保存到 token 里面
                    .withAudience(userID)
                    // 五分钟后token过期
                    .withExpiresAt(date)
                    // token 的密钥
                    .sign(algorithm);
        } catch (Exception e) {
            return null;
        }
    }

    public static User getCurrentUser(){
        HttpServletRequest request= ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String token =request.getHeader("token");
        String userID =JWT.decode(token).getAudience().get(0);
        return staticUserService.getById(userID);
    }


}
