package com.nchu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.nchu.pojo.UserVideo;
import com.nchu.service.UserVideoService;
import com.nchu.mapper.UserVideoMapper;
import com.nchu.feign.utils.PageBean;
import com.nchu.feign.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author qhl666
* @description 针对表【user_video】的数据库操作Service实现
* @createDate 2024-04-10 11:00:45
*/
@Service
public class UserVideoServiceImpl extends ServiceImpl<UserVideoMapper, UserVideo>
    implements UserVideoService{
    @Autowired
    private UserVideoMapper userVideoMapper;

    @Override
    public Result getVideos(int userid, int pageSize, int currentPage) {
        PageHelper.startPage(currentPage,pageSize);
        List<Integer> pageList = userVideoMapper.queryByPage(userid);
        PageInfo<Integer> pageInfo = new PageInfo<>(pageList);
        PageBean<Integer> pageBean = new PageBean<>(pageInfo.getPageNum(),pageInfo.getPageSize(),pageInfo.getTotal(),pageInfo.getList());
        return Result.ok(pageBean);
    }

    @Override
    public Result addVideo(UserVideo userVideo) {
        userVideoMapper.insert(userVideo);
        return Result.ok(null);
    }

    @Override
    public Result deleteVideo(UserVideo userVideo) {
        LambdaQueryWrapper<UserVideo> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(UserVideo::getUserid,userVideo.getUserid());
        lambdaQueryWrapper.eq(UserVideo::getVideoid,userVideo.getVideoid());
        userVideoMapper.delete(lambdaQueryWrapper);
        return Result.ok(null);
    }

    @Override
    public Result get(int videoid) {
        LambdaQueryWrapper<UserVideo> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(UserVideo::getVideoid,videoid);
        UserVideo userVideo = userVideoMapper.selectOne(lambdaQueryWrapper);
        return Result.ok(userVideo.getUserid());
    }
}




