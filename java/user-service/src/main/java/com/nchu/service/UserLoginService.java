package com.nchu.service;

import com.nchu.pojo.UserLogin;
import com.baomidou.mybatisplus.extension.service.IService;
import com.nchu.feign.utils.Result;

/**
* @author qhl666
* @description 针对表【user_login】的数据库操作Service
* @createDate 2024-04-10 11:00:45
*/
public interface UserLoginService extends IService<UserLogin> {

    Result getLogins(int userid, int pageSize, int currentPage);

    Result add(UserLogin userLogin);
}
