package com.nchu.controller;

import com.nchu.pojo.UserLogin;
import com.nchu.service.UserLoginService;
import com.nchu.feign.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("userLogin")
public class UserLoginController {
    @Autowired
    private UserLoginService userLoginService;
    @GetMapping("{userid}/{pageSize}/{currentPage}")
    public Result getLogins(@PathVariable(name = "userid") int userid,
                              @PathVariable(name = "pageSize") int pageSize,
                              @PathVariable(name = "currentPage") int currentPage){
        return userLoginService.getLogins(userid, pageSize,currentPage);
    }
    @PostMapping()
    public Result add(@RequestBody UserLogin userLogin){
        return userLoginService.add(userLogin);
    }
}
