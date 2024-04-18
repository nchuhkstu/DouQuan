package com.nchu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.nchu.feign.clinets.CommentClient;
import com.nchu.feign.pojo.Comment;
import com.nchu.feign.utils.JwtHelper;
import com.nchu.feign.utils.PageBean;
import com.nchu.feign.utils.Result;
import com.nchu.feign.utils.ResultCodeEnum;
import com.nchu.mapper.ArticleCommentMapper;
import com.nchu.pojo.ArticleComment;
import com.nchu.service.ArticleCommentService;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
* @author qhl666
* @description 针对表【article_comment】的数据库操作Service实现
* @createDate 2024-04-10 18:52:21
*/
@Service
public class ArticleCommentServiceImpl extends ServiceImpl<ArticleCommentMapper, ArticleComment>
    implements ArticleCommentService{
    @Autowired
    private ArticleCommentMapper articleCommentMapper;
    @Autowired
    private CommentClient commentClient;
    @Autowired
    private JwtHelper jwtHelper;
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Override
    public Result getArticleComments(int articleid, int pageSize, int currentPage) {
        PageHelper.startPage(currentPage,pageSize);
        List<Integer> pageList = articleCommentMapper.queryByPage(articleid);
        List<Object> list = new ArrayList<>();
        for(Integer commentId : pageList){
            Object commentDetail = commentClient.get(commentId).getData();
            list.add(commentDetail);
        }
        PageInfo<Integer> pageInfo = new PageInfo<>(pageList);
        PageBean<Object> pageBean = new PageBean<>(pageInfo.getPageNum(),pageInfo.getPageSize(),pageInfo.getTotal(),list);
        return Result.ok(pageBean);
    }

    @Override
    public Result addArticleComment(Comment comment,String token,int articleid) {
        if(jwtHelper.isExpiration(token))
            return Result.build(null, ResultCodeEnum.NOTLOGIN);
        int userId = jwtHelper.getUserId(token).intValue();
        comment.setUserid(userId);
        comment.setCommenttime(new Timestamp(System.currentTimeMillis()));
        int commentid = Integer.parseInt(commentClient.add(comment).getData().toString());
        articleCommentMapper.insert(new ArticleComment(articleid,commentid));
        return Result.ok(commentid);
    }

    @Override
    public Result deleteArticleComment(int commentid,String token) {
        if(jwtHelper.isExpiration(token))
            return Result.build(null, ResultCodeEnum.NOTLOGIN);
        rabbitTemplate.convertAndSend("article","articleComment.delete",commentid);
        articleCommentMapper.deleteById(commentid);
        return Result.ok(null);
    }

    @Override
    public Result getCount(int articleid) {
        LambdaQueryWrapper<ArticleComment> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(ArticleComment::getArticleid,articleid);
        Long count = articleCommentMapper.selectCount(lambdaQueryWrapper);
        return Result.ok(count);
    }

    @Override
    public Result delete(int articleid) {
        LambdaQueryWrapper<ArticleComment> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(ArticleComment::getArticleid,articleid);

        List<ArticleComment> list = articleCommentMapper.selectList(lambdaQueryWrapper);
        for(ArticleComment articleComment : list){
            rabbitTemplate.convertAndSend("article","articleComment.delete",articleComment.getCommentid());
        }
        articleCommentMapper.delete(lambdaQueryWrapper);
        return Result.ok(null);
    }
}




