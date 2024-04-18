package com.nchu.service;

import com.nchu.feign.utils.Result;
import com.nchu.pojo.ArticleLike;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author qhl666
* @description 针对表【article_like】的数据库操作Service
* @createDate 2024-04-10 18:52:21
*/
public interface ArticleLikeService extends IService<ArticleLike> {

    Result getCount(int articleid);

    Result addLike(int articleid,String token);

    Result deleteLike(String token, int articleid);

    Result isLike(int articleid, String token);

    Result delete(int articleid);
}
