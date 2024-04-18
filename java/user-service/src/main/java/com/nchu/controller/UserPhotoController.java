package com.nchu.controller;

import com.nchu.pojo.UserPhoto;
import com.nchu.service.UserPhotoService;
import com.nchu.feign.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("userPhoto")
public class UserPhotoController {
    @Autowired
    private UserPhotoService userPhotoService;
    @GetMapping("{userid}")
    public Result get(@PathVariable(name = "userid") int userid){
        return userPhotoService.get(userid);
    }
    @DeleteMapping()
    public Result delete(@RequestBody UserPhoto userPhoto){
        return userPhotoService.delete(userPhoto);
    }
}
