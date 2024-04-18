package com.nchu.service;

import com.nchu.feign.utils.Result;
import com.nchu.pojo.Like;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author qhl666
* @description 针对表【like】的数据库操作Service
* @createDate 2024-04-13 15:48:39
*/
public interface LikeService extends IService<Like> {

    Result get(int likeid);

    Result add(Like like);

    Result delete(int likeid);

    Result getUserid(int likeid);
}
