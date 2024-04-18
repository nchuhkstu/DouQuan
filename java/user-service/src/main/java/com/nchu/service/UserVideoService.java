package com.nchu.service;

import com.nchu.pojo.UserVideo;
import com.baomidou.mybatisplus.extension.service.IService;
import com.nchu.feign.utils.Result;

/**
* @author qhl666
* @description 针对表【user_video】的数据库操作Service
* @createDate 2024-04-10 11:00:45
*/
public interface UserVideoService extends IService<UserVideo> {

    Result getVideos(int userid, int pageSize, int currentPage);

    Result addVideo(UserVideo userVideo);

    Result deleteVideo(UserVideo userVideo);

    Result get(int videoid);
}
