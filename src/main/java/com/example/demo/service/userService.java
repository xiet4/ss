package com.example.demo.service;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.entity.User;
import com.example.demo.entity.dto.userDto;
import com.example.demo.mapper.Usermapper;
import org.springframework.stereotype.Service;

@Service
public class userService extends ServiceImpl<Usermapper, User> {

    public boolean login(userDto userdto) {
        String username =userdto.getUsername();
        String password = userdto.getPassword();
        if(StrUtil.isBlank(username)||StrUtil.isBlank(password)){
            return false;
        }
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username",username);
        queryWrapper.eq("password",password);
        User one =getOne(queryWrapper);
        return one!=null;
    }
}
