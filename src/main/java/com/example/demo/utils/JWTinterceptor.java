package com.example.demo.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.example.demo.common.Constants;
import com.example.demo.entity.User;
import com.example.demo.exception.ServiceException;
import com.example.demo.service.userService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

public class JWTinterceptor implements HandlerInterceptor {

    @Autowired
    private userService userService;

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object object) {
        // 从 http 请求头中取出 token
        String token = httpServletRequest.getHeader("token");
        // 如果不是映射到方法直接通过
        if (!(object instanceof HandlerMethod)) {
            return true;
        }
        if(StringUtils.isEmpty(token)){
            throw new ServiceException(Constants.CODE_401,"无token");
        }
        String userID;
        try{
            userID = JWT.decode(token).getAudience().get(0);
        }catch (JWTDecodeException e){
            throw  new ServiceException(Constants.CODE_401,"token验证失败");
        }
        User user = userService.getById(userID);

        if ((user==null)){
            throw  new ServiceException(Constants.CODE_401,"用户不存在,token验证失败");
        }
        JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(user.getPassword())).build();
        try {
            jwtVerifier.verify(token);
        } catch (JWTVerificationException e) {
            throw  new ServiceException(Constants.CODE_401,"token验证失败");
        }

        return true;
    }


}
