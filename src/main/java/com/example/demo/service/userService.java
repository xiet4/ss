package com.example.demo.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.common.Constants;
import com.example.demo.common.Result;
import com.example.demo.entity.User;
import com.example.demo.entity.dto.userDto;
import com.example.demo.exception.ServiceException;
import com.example.demo.mapper.Usermapper;
import com.example.demo.utils.Token;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

@Service
public class userService extends ServiceImpl<Usermapper, User> {

    public userDto login(userDto userdto) {
        String username =userdto.getUsername();
        String password = userdto.getPassword();
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username",username);
        queryWrapper.eq("password",password);
        User one =getOne(queryWrapper);
        if (one!=null){
        BeanUtil.copyProperties(one,userdto,true);
        String token= Token.getToken(one.getId().toString(),one.getPassword());
        userdto.setToken(token);
        return userdto;
        }else {
            throw new ServiceException(Constants.CODE_500,"用户不存在");
        }
    }
}
