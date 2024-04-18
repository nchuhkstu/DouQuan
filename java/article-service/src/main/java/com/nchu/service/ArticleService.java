package com.nchu.service;

import com.nchu.feign.utils.Result;
import com.nchu.pojo.Article;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author qhl666
* @description 针对表【article】的数据库操作Service
* @createDate 2024-04-10 18:52:21
*/
public interface ArticleService extends IService<Article> {

    Result get(int articleid);

    Result add(String token, Article article);

    Result delete(int articleid);

    Result getDetail(int articleid);

    Result getHot(int pageSize, int currentPage);
}
