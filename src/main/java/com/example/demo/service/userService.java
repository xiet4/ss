package com.example.demo.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.entity.User;
import com.example.demo.mapper.Usermapper;
import org.springframework.stereotype.Service;

@Service
public class userService extends ServiceImpl<Usermapper, User> {

}
