package com.nchu.service;

import com.nchu.pojo.UserExtend;
import com.baomidou.mybatisplus.extension.service.IService;
import com.nchu.feign.utils.Result;

/**
* @author qhl666
* @description 针对表【user_extend】的数据库操作Service
* @createDate 2024-04-10 11:00:45
*/
public interface UserExtendService extends IService<UserExtend> {

    Result get(Long userid);

    Result put(UserExtend userExtend, String token);

    Result getDetail(int userid);

    Result add(UserExtend userExtend);

    Result uploadAvatar(int photoid, String token);
}
