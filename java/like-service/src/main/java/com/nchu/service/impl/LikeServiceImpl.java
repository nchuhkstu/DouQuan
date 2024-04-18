package com.nchu.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nchu.feign.utils.Result;
import com.nchu.pojo.Like;
import com.nchu.service.LikeService;
import com.nchu.mapper.LikeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
* @author qhl666
* @description 针对表【like】的数据库操作Service实现
* @createDate 2024-04-13 15:48:39
*/
@Service
public class LikeServiceImpl extends ServiceImpl<LikeMapper, Like>
    implements LikeService{
    @Autowired
    private LikeMapper likeMapper;

    @Override
    public Result get(int likeid) {
        Like like = likeMapper.selectById(likeid);
        return Result.ok(like);
    }

    @Override
    public Result add(Like like) {
        likeMapper.insert(like);
        return Result.ok(like.getLikeid());
    }

    @Override
    public Result delete(int likeid) {
        likeMapper.deleteById(likeid);
        return Result.ok(null);
    }

    @Override
    public Result getUserid(int likeid) {
        return Result.ok(likeMapper.selectById(likeid).getUserid());
    }
}




