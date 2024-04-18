package com.nchu.service;

import com.nchu.feign.pojo.Comment;
import com.nchu.feign.utils.Result;
import com.nchu.pojo.ArticleComment;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author qhl666
* @description 针对表【article_comment】的数据库操作Service
* @createDate 2024-04-10 18:52:21
*/
public interface ArticleCommentService extends IService<ArticleComment> {

    Result getArticleComments(int articleid, int pageSize, int currentPage);

    Result addArticleComment(Comment comment,String token,int articleid);

    Result deleteArticleComment(int commentid,String token);

    Result getCount(int articleid);

    Result delete(int articleid);
}
