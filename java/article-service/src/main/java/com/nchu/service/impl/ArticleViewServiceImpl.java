package com.nchu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nchu.feign.utils.Result;
import com.nchu.mapper.ArticleViewMapper;
import com.nchu.pojo.ArticleView;
import com.nchu.pojo.ArticleView;
import com.nchu.service.ArticleViewService;
import com.nchu.service.ArticleViewService;
import com.nchu.mapper.ArticleViewMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
* @author qhl666
* @description 针对表【article_view】的数据库操作Service实现
* @createDate 2024-04-10 18:52:21
*/
@Service
public class ArticleViewServiceImpl extends ServiceImpl<ArticleViewMapper, ArticleView>
        implements ArticleViewService {
    @Autowired
    private ArticleViewMapper articleViewMapper;

    @Override
    public Result getCount(int articleid) {
        LambdaQueryWrapper<ArticleView> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(ArticleView::getArticleid,articleid);
        Long count = articleViewMapper.selectCount(lambdaQueryWrapper);
        return Result.ok(count);
    }

    @Override
    public Result addView(ArticleView articleView) {
        articleViewMapper.insert(articleView);
        return Result.ok(null);
    }

    @Override
    public Result deleteView(ArticleView articleView) {
        articleViewMapper.deleteById(articleView);
        return Result.ok(null);
    }
}




