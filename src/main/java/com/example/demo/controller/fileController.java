package com.example.demo.controller;

import cn.hutool.json.JSONObject;
import com.example.demo.common.Constants;
import com.example.demo.common.Result;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

@RestController
@RequestMapping("/file")
public class fileController {
    @Value("${file.upload.url}")
    private String uploadFilePath;

    @PostMapping("/upload")
    public Result httpUpload(@RequestParam MultipartFile file){

            String fileName = file.getOriginalFilename();  // 文件名
            File dest = new File(uploadFilePath +'/'+ fileName);
            if (!dest.getParentFile().exists()) {
                dest.getParentFile().mkdirs();
            }
            try {
                file.transferTo(dest);
            } catch (Exception e) {
            return Result.error(Constants.CODE_500,"上传失败");
            }

        return Result.success(Constants.CODE_200);
    }




}
