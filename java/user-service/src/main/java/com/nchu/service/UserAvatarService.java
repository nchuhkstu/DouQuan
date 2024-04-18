package com.nchu.service;

import com.nchu.pojo.UserAvatar;
import com.baomidou.mybatisplus.extension.service.IService;
import com.nchu.feign.utils.Result;

/**
* @author qhl666
* @description 针对表【user_avatar】的数据库操作Service
* @createDate 2024-04-11 12:43:36
*/
public interface UserAvatarService extends IService<UserAvatar> {

    Result get(int userid);

    Result delete(UserAvatar userAvatar);

    Result getDetail(int userid);

    Result add(UserAvatar userAvatar, String token);
}
