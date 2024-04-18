package com.nchu.controller;

import com.nchu.pojo.UserExtend;
import com.nchu.service.UserExtendService;
import com.nchu.feign.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("userExtend")
public class UserExtendController {
    @Autowired
    private UserExtendService userExtendService;
    @GetMapping("{userid}")
    public Result get(@PathVariable(name = "userid") Long userid){
        return userExtendService.get(userid);
    }
    @PostMapping()
    public Result add(@RequestBody UserExtend userExtend){
        return userExtendService.add(userExtend);
    }
    @PutMapping()
    public Result put(@RequestBody UserExtend userExtend,
                      @RequestHeader("X-token")String token){
        return userExtendService.put(userExtend,token);
    }
    @GetMapping("/detail/{userid}")
    public Result getDetail(@PathVariable(name = "userid")int userid){
        return userExtendService.getDetail(userid);
    }
    @GetMapping("avatar/{photoid}")
    public Result uploadAvatar(@PathVariable(name = "photoid")int photoid,
                               @RequestHeader(name = "X-token")String token){
        return userExtendService.uploadAvatar(photoid,token);
    }
}
