package com.nchu.service;

import com.nchu.feign.utils.Result;
import com.nchu.pojo.Comment;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author qhl666
* @description 针对表【comment】的数据库操作Service
* @createDate 2024-04-13 20:26:31
*/
public interface CommentService extends IService<Comment> {

    Result add(Comment comment);

    Result delete(int commentid);

    Result get(int commentid);
}
