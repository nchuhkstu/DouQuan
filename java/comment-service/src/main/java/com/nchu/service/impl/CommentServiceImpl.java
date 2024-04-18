package com.nchu.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nchu.feign.clinets.UserExtendClient;
import com.nchu.feign.utils.Result;
import com.nchu.pojo.Comment;
import com.nchu.service.CommentService;
import com.nchu.mapper.CommentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;

/**
* @author qhl666
* @description 针对表【comment】的数据库操作Service实现
* @createDate 2024-04-13 20:26:31
*/
@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment>
    implements CommentService{
    @Autowired
    private CommentMapper commentMapper;
    @Autowired
    private UserExtendClient userExtendClient;

    @Override
    public Result add(Comment comment) {
        commentMapper.insert(comment);
        return Result.ok(comment.getCommentid());
    }

    @Override
    public Result delete(int commentid) {
        commentMapper.deleteById(commentid);
        return Result.ok(null);
    }

    @Override
    public Result get(int commentid) {
        Comment comment = commentMapper.selectById(commentid);
        int userid = commentMapper.selectById(commentid).getUserid();
        Object object = userExtendClient.getDetail(userid).getData();
        HashMap<String,Object> hashMap = new HashMap<>();
        hashMap.put("commentid",commentid);
        hashMap.put("content",comment.getContent());
        hashMap.put("commentTime",comment.getCommenttime());
        hashMap.put("user",object);
        return Result.ok(hashMap);
    }
}




