package com.nchu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nchu.feign.clinets.LikeClient;
import com.nchu.feign.pojo.Like;
import com.nchu.feign.utils.JwtHelper;
import com.nchu.feign.utils.Result;
import com.nchu.feign.utils.ResultCodeEnum;
import com.nchu.pojo.ArticleLike;
import com.nchu.service.ArticleLikeService;
import com.nchu.mapper.ArticleLikeMapper;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
* @author qhl666
* @description 针对表【article_like】的数据库操作Service实现
* @createDate 2024-04-10 18:52:21
*/
@Service
public class ArticleLikeServiceImpl extends ServiceImpl<ArticleLikeMapper, ArticleLike>
    implements ArticleLikeService{
    @Autowired
    private ArticleLikeMapper articleLikeMapper;
    @Autowired
    private LikeClient likeClient;
    @Autowired
    private JwtHelper jwtHelper;
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Override
    public Result getCount(int articleid) {
        LambdaQueryWrapper<ArticleLike> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(ArticleLike::getArticleid,articleid);
        Long count = articleLikeMapper.selectCount(lambdaQueryWrapper);
        return Result.ok(count);
    }

    @Override
    public Result addLike(int articleid,String token) {
        if(jwtHelper.isExpiration(token))
            return Result.build(null, ResultCodeEnum.NOTLOGIN);
        int userId = jwtHelper.getUserId(token).intValue();
        Like like = new Like(null,userId,new Timestamp(System.currentTimeMillis()));
        int likeid = Integer.parseInt(likeClient.add(like).getData().toString());
        articleLikeMapper.insert(new ArticleLike(articleid,likeid));
        return Result.ok(null);
    }

    @Override
    public Result deleteLike(String token,int articleid) {
        if(jwtHelper.isExpiration(token))
            return Result.build(null, ResultCodeEnum.NOTLOGIN);
        int userId = jwtHelper.getUserId(token).intValue();
        LambdaQueryWrapper<ArticleLike> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(ArticleLike::getArticleid,articleid);
        List<ArticleLike> list = articleLikeMapper.selectList(lambdaQueryWrapper);
        for(ArticleLike articleLike : list){
            Integer likeid = articleLike.getLikeid();
            int get_Userid = Integer.parseInt(likeClient.get(likeid).getData().toString());
            if(get_Userid == userId){
                articleLikeMapper.deleteById(articleLike);
                rabbitTemplate.convertAndSend("article","articleLike.delete",likeid);
            }
        }
        return Result.ok(null);
    }

    @Override
    public Result isLike(int articleid, String token) {
        if(jwtHelper.isExpiration(token))
            return Result.build(null, ResultCodeEnum.NOTLOGIN);
        int userId = jwtHelper.getUserId(token).intValue();
        LambdaQueryWrapper<ArticleLike> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(ArticleLike::getArticleid,articleid);
        List<ArticleLike> list = articleLikeMapper.selectList(lambdaQueryWrapper);
        for(ArticleLike articleLike : list){
            Integer likeid = articleLike.getLikeid();
            int get_Userid = Integer.parseInt(likeClient.get(likeid).getData().toString());
            if(get_Userid == userId)
                return Result.ok(true);
        }
        return Result.ok(false);
    }

    @Override
    public Result delete(int articleid) {
        LambdaQueryWrapper<ArticleLike> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(ArticleLike::getArticleid,articleid);
        List<ArticleLike> list = articleLikeMapper.selectList(lambdaQueryWrapper);
        for(ArticleLike articleLike : list){
            rabbitTemplate.convertAndSend("article","articleLike.delete",articleLike.getLikeid());
        }
        articleLikeMapper.delete(lambdaQueryWrapper);
        return Result.ok(null);
    }
}




