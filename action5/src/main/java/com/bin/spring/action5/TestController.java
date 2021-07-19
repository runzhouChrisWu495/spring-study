package com.bin.spring.action5;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Controller;
import org.springframework.util.ClassUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

@Slf4j
@Controller
public class TestController {
    @ResponseBody
    @GetMapping("/get")
    public String get(@RequestAttribute String name){
        return "get";
    }

    @GetMapping("/variables")
    @ResponseBody
    public String getVariables(String name, String password, HashMap<String,String> map){
        map.put("k","v");
        return name+password+map.get("k");
    }

    @ResponseBody
    @PostMapping("/save")
    public Car save(@RequestParam Car car){
        return  car;
    }


    @ResponseBody
    @GetMapping("/he11")
    public FileSystemResource fileSystemResource(){
        return null;
    }

    @ResponseBody
    @GetMapping("/getCar")
    public Car getCar(){
        Car car = new Car();
        car.setPrice(13);
        car.setBrand("byd");
        return car;
    }


    // 文件上传使用MultipartFile类
    @PostMapping("/upload")
    public String upload(@RequestParam(value = "email",required = false) String email,
                         @RequestParam(value = "username",required = false) String username,
                         @RequestPart(value = "headerImg",required = false) MultipartFile headerImg,
                         @RequestPart(value = "photos",required = false) MultipartFile[] photos) throws IOException {

        log.info("上传的信息：email={}，username={}，headerImg={}，photos={}",
                email,username,headerImg.getSize(),photos.length);

        if(!headerImg.isEmpty()){
            //保存到文件服务器，OSS服务器
            String originalFilename = headerImg.getOriginalFilename();
            headerImg.transferTo(new File("F:\\cache\\"+originalFilename));
        }

        if(photos.length > 0){
            for (MultipartFile photo : photos) {
                if(!photo.isEmpty()){
                    String originalFilename = photo.getOriginalFilename();
                    photo.transferTo(new File("F:\\cache\\"+originalFilename));
                }
            }
        }

        return "index";
    }
}
