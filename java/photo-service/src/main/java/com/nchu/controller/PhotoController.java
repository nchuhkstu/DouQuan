package com.nchu.controller;

import com.nchu.feign.utils.Result;
import com.nchu.pojo.Photo;
import com.nchu.service.PhotoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@CrossOrigin
@RequestMapping("photo")
public class PhotoController {
    @Autowired
    private PhotoService photoService;
    @GetMapping("{photoid}")
    public Result get(@PathVariable(name = "photoid")int photoid){
        return photoService.get(photoid);
    }
    @DeleteMapping("{photoid}")
    public Result delete(@PathVariable(name = "photoid")int photoid){
        return photoService.delete(photoid);
    }
    @PostMapping("/upload")
    public Result upload(@RequestHeader("X-token") String token,@RequestParam("files") MultipartFile[] files){
        return photoService.upload(token, files);
    }
}
