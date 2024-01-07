package com.example.demo.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.entity.User;
import com.example.demo.mapper.Usermapper;
import com.example.demo.service.userService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RequestMapping("/user")
@RestController
public class UserController {

    @Autowired
    private userService userService;

    @GetMapping("/")
    public List<User> findAll(){
        return userService.list();
    }

    @PostMapping("/save")
    public boolean save(@RequestBody User user){
        return userService.saveOrUpdate(user);

    }

    @DeleteMapping("/delete/{id}")
    public boolean delete(@PathVariable int id){
        return userService.removeById(id);
    }

    @PostMapping("/delbatch")
    public boolean delete(@RequestBody List<Integer> ids){
        return userService.removeBatchByIds(ids);
    }

    @GetMapping("/page")
    public IPage<User> findPage(@RequestParam Integer pageNum,@RequestParam Integer pageSize,
                                @RequestParam(defaultValue = "") String username,
                                @RequestParam(defaultValue = "") String nickname,
                                @RequestParam(defaultValue = "") String email,
                                @RequestParam(defaultValue = "") String phone,
                                @RequestParam(defaultValue = "") String address
                                ){
        IPage<User> page =new Page<>(pageNum,pageSize);
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        if(!"".equals(username)){
        queryWrapper.like("username",username);
        }
        if(!"".equals(nickname)){
        queryWrapper.like("nickname",nickname);
        }
        if(!"".equals(email)){
        queryWrapper.like("email",email);}
        if(!"".equals(phone)){
        queryWrapper.like("phone",phone);}
        if(!"".equals(address)){
        queryWrapper.like("address",address);}
        queryWrapper.orderByDesc("id");
        return userService.page(page,queryWrapper);

    }
}
