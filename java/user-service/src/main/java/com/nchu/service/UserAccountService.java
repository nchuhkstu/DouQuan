package com.nchu.service;

import com.nchu.pojo.UserAccount;
import com.baomidou.mybatisplus.extension.service.IService;
import com.nchu.feign.utils.Result;

/**
* @author qhl666
* @description 针对表【user_account】的数据库操作Service
* @createDate 2024-04-10 11:00:45
*/
public interface UserAccountService extends IService<UserAccount> {

    Result login(UserAccount userAccount);

    Result register(UserAccount userAccount);

    Result isLogin(String token);

    Result getUserid(String token);

    Result getDetail(String token);

    Result getDetail2(Long userid);
}
