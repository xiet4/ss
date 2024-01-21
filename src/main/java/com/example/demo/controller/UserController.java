package com.example.demo.controller;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.common.Constants;
import com.example.demo.common.Result;
import com.example.demo.entity.User;
import com.example.demo.entity.dto.userDto;
import com.example.demo.mapper.Usermapper;
import com.example.demo.service.userService;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;


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
    @GetMapping("/find/{username}")
    public User findByName(@PathVariable String username){
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
         queryWrapper.eq("username",username);
        return userService.getOne(queryWrapper);
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

    @GetMapping("/export")
    public void export(HttpServletResponse response) throws IOException {
           List<User> list = userService.list();
        ExcelWriter excelWriter = ExcelUtil.getWriter(true);

        excelWriter.write(list,true);

        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8");
        response.setHeader("Content-Disposition","attachment;filename=test.xlsx");

        ServletOutputStream out = response.getOutputStream();
        excelWriter.flush(out, true);
        excelWriter.close();
        out.close();
    }

    @PostMapping("/import")
    public void imp(MultipartFile file) throws IOException {
        InputStream inputStream = file.getInputStream();
        ExcelReader reader = ExcelUtil.getReader(inputStream);

         List<List<Object>> list = reader.read(1);
        List<User> users = CollUtil.newArrayList();
        for(List<Object> row:list ){
         User user=new User();
         user.setUsername(row.get(1).toString());
         user.setPassword(row.get(2).toString());
         user.setNickname(row.get(3).toString());
         user.setEmail(row.get(4).toString());
            user.setPhone(row.get(5).toString());
            user.setAddress(row.get(6).toString());
            users.add(user);
         }

        userService.saveBatch(users);
        System.out.println(list);

    }

    @PostMapping("/login")//resquestbody 把前端的JSON转成java对象
    public Result login(@RequestBody userDto userdto){

        String username =userdto.getUsername();
        String password = userdto.getPassword();
        if(StrUtil.isBlank(username)||StrUtil.isBlank(password)){
            return Result.error(Constants.CODE_500,"ss");
        }
        return Result.success(userService.login(userdto));
    }

}
