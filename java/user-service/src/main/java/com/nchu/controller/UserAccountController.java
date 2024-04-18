package com.nchu.controller;

import com.nchu.pojo.UserAccount;
import com.nchu.service.UserAccountService;
import com.nchu.feign.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("userAccount")
@CrossOrigin
public class UserAccountController {
    @Autowired
    private UserAccountService userAccountService;
    @PostMapping("login")
    public Result login(@RequestBody UserAccount userAccount){
        return userAccountService.login(userAccount);
    }
    @PostMapping("register")
    public Result register(@RequestBody UserAccount userAccount){
        return userAccountService.register(userAccount);
    }
    @GetMapping()
    public Result isLogin(@RequestHeader("X-token") String token){
        return userAccountService.isLogin(token);
    }
    @GetMapping("userid")
    public Result getUserid(@RequestHeader("X-token")String token){
        return userAccountService.getUserid(token);
    }
    @GetMapping("detail")
    public Result getDetail(@RequestHeader("X-token")String token){
        return userAccountService.getDetail(token);
    }
    @GetMapping("detail/{userid}")
    public Result getDetail2(@PathVariable(name = "userid")Long userid){
        return userAccountService.getDetail2(userid);
    }

}
