package com.nchu.service;

import com.nchu.feign.utils.Result;
import com.nchu.pojo.ArticleView;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author qhl666
* @description 针对表【article_view】的数据库操作Service
* @createDate 2024-04-10 18:52:21
*/
public interface ArticleViewService extends IService<ArticleView> {

    Result getCount(int articleid);

    Result addView(ArticleView articleView);

    Result deleteView(ArticleView articleView);
}
