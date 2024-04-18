package com.nchu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.nchu.feign.clinets.UserArticleClient;
import com.nchu.feign.clinets.UserExtendClient;
import com.nchu.feign.pojo.UserArticle;
import com.nchu.feign.utils.JwtHelper;
import com.nchu.feign.utils.PageBean;
import com.nchu.feign.utils.Result;
import com.nchu.feign.utils.ResultCodeEnum;
import com.nchu.mapper.*;
import com.nchu.pojo.*;
import com.nchu.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
* @author qhl666
* @description 针对表【article】的数据库操作Service实现
* @createDate 2024-04-10 18:52:21
*/
@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article>
    implements ArticleService{
    @Autowired
    private ArticleMapper articleMapper;
    @Autowired
    private ArticlePhotoService articlePhotoService;
    @Autowired
    private ArticleViewService articleViewService;
    @Autowired
    private ArticleLikeService articleLikeService;
    @Autowired
    private ArticleCommentService articleCommentService;
    @Autowired
    private ArticleRelayService articleRelayService;
    @Autowired
    private JwtHelper jwtHelper;
    @Autowired
    private UserArticleClient userArticleClient;
    @Autowired
    private UserExtendClient userExtendClient;

    @Override
    public Result get(int articleid) {
        Article article = articleMapper.selectById(articleid);
        return Result.ok(article);
    }

    @Override
    public Result add(String token, Article article) {
        if(jwtHelper.isExpiration(token))
            return Result.build(null, ResultCodeEnum.NOTLOGIN);
        article.setPublishtime(new Timestamp(System.currentTimeMillis()));
        articleMapper.insert(article);
        int userId = jwtHelper.getUserId(token).intValue();
        UserArticle userArticle = new UserArticle(userId,article.getArticleid());
        userArticleClient.addArticles(userArticle);
        return Result.ok(article.getArticleid());
    }

    @Override
    public Result delete(int articleid) {
        LambdaQueryWrapper<Article> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Article::getArticleid,articleid);
        articlePhotoService.delete(articleid);
        articleLikeService.delete(articleid);
        articleCommentService.delete(articleid);
        articleRelayService.delete(articleid);
        articleMapper.delete(lambdaQueryWrapper);
        return Result.ok(null);
    }

    @Override
    public Result getDetail(int articleid) {
        Article article = articleMapper.selectById(articleid);
        List<String> list = (List<String>) articlePhotoService.get(articleid).getData();
        int userid = Integer.parseInt(userArticleClient.getUserid(articleid).getData().toString());
        try {
            HashMap<String, Object> hashmap = new HashMap<String, Object>();
            hashmap.put("user",userExtendClient.getDetail(userid).getData());
            hashmap.put("viewNumber",articleViewService.getCount(articleid).getData());
            hashmap.put("likeNumber",articleLikeService.getCount(articleid).getData());
            hashmap.put("commentNumber",articleCommentService.getCount(articleid).getData());
            hashmap.put("relayNumber",articleRelayService.getCount(articleid).getData());
            hashmap.put("article",article);
            hashmap.put("images",list);
            return Result.ok(hashmap);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Result.ok(null);
    }

    @Override
    public Result getHot(int pageSize, int currentPage) {
        PageHelper.startPage(currentPage,pageSize);
        List<Integer> pageList = articleMapper.findByPageHot();
        List<Object> list = new ArrayList<>();
        for(Integer articleId : pageList){
            Object articleDetail = getDetail(articleId).getData();
            list.add(articleDetail);
        }
        PageInfo<Integer> pageInfo = new PageInfo<>(pageList);
        PageBean<Object> pageBean = new PageBean<>(pageInfo.getPageNum(),pageInfo.getPageSize(),pageInfo.getTotal(),list);
        return Result.ok(pageBean);
    }
}




