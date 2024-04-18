package com.nchu.service;

import com.nchu.pojo.UserArticle;
import com.baomidou.mybatisplus.extension.service.IService;
import com.nchu.feign.utils.Result;

/**
* @author qhl666
* @description 针对表【user_article】的数据库操作Service
* @createDate 2024-04-10 11:00:45
*/
public interface UserArticleService extends IService<UserArticle> {

    Result getArticles(int userid, int pageSize, int currentPage);

    Result addArticles(UserArticle userArticle);

    Result deleteArticles(UserArticle userArticle);

    Result getUser(int articleid);
}
