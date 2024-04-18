package com.nchu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nchu.feign.utils.Result;
import com.nchu.pojo.ArticleComment;
import com.nchu.pojo.ArticleLike;
import com.nchu.pojo.ArticleRelay;
import com.nchu.service.ArticleRelayService;
import com.nchu.mapper.ArticleRelayMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
* @author qhl666
* @description 针对表【article_relay】的数据库操作Service实现
* @createDate 2024-04-10 18:52:21
*/
@Service
public class ArticleRelayServiceImpl extends ServiceImpl<ArticleRelayMapper, ArticleRelay>
    implements ArticleRelayService{
    @Autowired
    private ArticleRelayMapper articleRelayMapper;

    @Override
    public Result getCount(int articleid) {
        LambdaQueryWrapper<ArticleRelay> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(ArticleRelay::getArticleid,articleid);
        Long count = articleRelayMapper.selectCount(lambdaQueryWrapper);
        return Result.ok(count);
    }

    @Override
    public Result addRelay(ArticleRelay articleRelay) {
        articleRelayMapper.insert(articleRelay);
        return Result.ok(null);
    }

    @Override
    public Result deleteRelay(ArticleRelay articleRelay) {
        articleRelayMapper.deleteById(articleRelay);
        return Result.ok(null);
    }

    @Override
    public Result delete(int articleid) {
        LambdaQueryWrapper<ArticleRelay> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(ArticleRelay::getArticleid,articleid);
        articleRelayMapper.delete(lambdaQueryWrapper);
        return Result.ok(null);
    }
}




