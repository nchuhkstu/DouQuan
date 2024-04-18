package com.nchu.service;

import com.nchu.pojo.UserPhoto;
import com.baomidou.mybatisplus.extension.service.IService;
import com.nchu.feign.utils.Result;

/**
* @author qhl666
* @description 针对表【user_photo】的数据库操作Service
* @createDate 2024-04-10 11:00:45
*/
public interface UserPhotoService extends IService<UserPhoto> {

    Result get(int userid);

    Result delete(UserPhoto userPhoto);
}
