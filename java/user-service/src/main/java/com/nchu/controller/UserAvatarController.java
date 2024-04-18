package com.nchu.controller;

import com.nchu.pojo.UserAvatar;
import com.nchu.service.UserAvatarService;
import com.nchu.feign.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("userAvatar")
public class UserAvatarController {
    @Autowired
    private UserAvatarService userAvatarService;
    @GetMapping("{userid}")
    public Result get(@PathVariable(name = "userid") int userid){
        return userAvatarService.get(userid);
    }
    @DeleteMapping()
    public Result delete(@RequestBody UserAvatar userAvatar){
        return userAvatarService.delete(userAvatar);
    }
    @GetMapping("detail/{userid}")
    public Result getDetail(@PathVariable(name = "userid") int userid){
        return userAvatarService.getDetail(userid);
    }
    @PostMapping()
    public Result add(@RequestBody UserAvatar userAvatar,
                      @RequestHeader("X-token")String token){
        return userAvatarService.add(userAvatar,token);
    }
}
