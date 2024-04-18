package com.nchu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nchu.feign.clinets.PhotoClient;
import com.nchu.feign.utils.Result;
import com.nchu.pojo.ArticlePhoto;
import com.nchu.service.ArticlePhotoService;
import com.nchu.mapper.ArticlePhotoMapper;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
* @author qhl666
* @description 针对表【article_photo】的数据库操作Service实现
* @createDate 2024-04-10 18:52:21
*/
@Service
public class ArticlePhotoServiceImpl extends ServiceImpl<ArticlePhotoMapper, ArticlePhoto>
    implements ArticlePhotoService{
    @Autowired
    private ArticlePhotoMapper articlePhotoMapper;
    @Autowired
    private PhotoClient photoClient;
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Override
    public Result get(int articleid) {

        List<Integer> list1 =(List<Integer>) get2(articleid).getData();
        List<String> list2 = new ArrayList<>();
        for (Integer integer : list1) {
            String url = photoClient.get(integer).getData().toString();
            list2.add(url);
        }
        return Result.ok(list2);
    }
    @Override
    public Result get2(int articleid) {
        LambdaQueryWrapper<ArticlePhoto> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(ArticlePhoto::getArticleid,articleid);
        List<ArticlePhoto> list = articlePhotoMapper.selectList(lambdaQueryWrapper);
        List<Integer> list1 = list.stream().map(ArticlePhoto::getPhotoid).toList();
        return Result.ok(list1);
    }

    @Override
    public Result add(ArticlePhoto articlePhoto) {
        articlePhotoMapper.insert(articlePhoto);
        return Result.ok(null);
    }

    @Override
    public Result delete(int articleid) {
        LambdaQueryWrapper<ArticlePhoto> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(ArticlePhoto::getArticleid,articleid);
        List<Integer> list = (List<Integer>) get2(articleid).getData();
        for(Integer integer : list){
            rabbitTemplate.convertAndSend("article","articlePhoto.delete",integer);
        }
        articlePhotoMapper.delete(lambdaQueryWrapper);

        return Result.ok(null);
    }


}




