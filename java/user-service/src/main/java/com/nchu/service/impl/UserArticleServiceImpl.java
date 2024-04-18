package com.nchu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.nchu.feign.clinets.ArticleClient;
import com.nchu.pojo.UserArticle;
import com.nchu.service.UserArticleService;
import com.nchu.mapper.UserArticleMapper;
import com.nchu.feign.utils.PageBean;
import com.nchu.feign.utils.Result;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
* @author qhl666
* @description 针对表【user_article】的数据库操作Service实现
* @createDate 2024-04-10 11:00:45
*/
@Service
public class UserArticleServiceImpl extends ServiceImpl<UserArticleMapper, UserArticle>
    implements UserArticleService{
    @Autowired
    private UserArticleMapper userArticleMapper;
    @Autowired
    private ArticleClient articleClient;
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Override
    public Result getArticles(int userid, int pageSize, int currentPage) {
        PageHelper.startPage(currentPage,pageSize);
        List<Integer> pageList = userArticleMapper.queryByPage(userid);
        List<Object> list = new ArrayList<>();
        for(Integer articleId : pageList){
            Object articleDetail = articleClient.getDetail(articleId).getData();
            list.add(articleDetail);
        }
        PageInfo<Object> pageInfo = new PageInfo<>(pageList);
        PageBean<Object> pageBean = new PageBean<>(pageInfo.getPageNum(),pageInfo.getPageSize(),pageInfo.getTotal(),list);
        return Result.ok(pageBean);
    }

    @Override
    public Result addArticles(UserArticle userArticle) {
        userArticleMapper.insert(userArticle);
        return Result.ok(null);
    }

    @Override
    public Result deleteArticles(UserArticle userArticle) {
        LambdaQueryWrapper<UserArticle> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(UserArticle::getArticleid, userArticle.getArticleid());
        userArticleMapper.delete(lambdaQueryWrapper);
        rabbitTemplate.convertAndSend("user","userArticle.delete",userArticle.getArticleid());
        return Result.ok(null);
    }

    @Override
    public Result getUser(int articleid) {
        UserArticle userArticle = userArticleMapper.selectById(articleid);
        return Result.ok(userArticle.getUserid());
    }
}




