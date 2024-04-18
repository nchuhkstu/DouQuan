package com.nchu.service;

import com.nchu.feign.utils.Result;
import com.nchu.pojo.ArticleRelay;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author qhl666
* @description 针对表【article_relay】的数据库操作Service
* @createDate 2024-04-10 18:52:21
*/
public interface ArticleRelayService extends IService<ArticleRelay> {

    Result getCount(int articleid);

    Result addRelay(ArticleRelay articleRelay);

    Result deleteRelay(ArticleRelay articleRelay);

    Result delete(int articleid);
}
