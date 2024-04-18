package com.nchu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nchu.feign.utils.Result;
import com.nchu.pojo.UserPhoto;
import com.nchu.service.UserPhotoService;
import com.nchu.mapper.UserPhotoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
* @author qhl666
* @description 针对表【user_photo】的数据库操作Service实现
* @createDate 2024-04-10 11:00:45
*/
@Service
public class UserPhotoServiceImpl extends ServiceImpl<UserPhotoMapper, UserPhoto>
    implements UserPhotoService{
    @Autowired
    private UserPhotoMapper userPhotoMapper;
    @Override
    public Result get(int userid) {
        LambdaQueryWrapper<UserPhoto> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(UserPhoto::getUserid,userid);
        List<UserPhoto> list = userPhotoMapper.selectList(lambdaQueryWrapper);
        List<Integer> list1 = list.stream().map(UserPhoto::getPhotoid).toList();
        return Result.ok(list1);
    }

    @Override
    public Result delete(UserPhoto userPhoto) {
        LambdaQueryWrapper<UserPhoto> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(UserPhoto::getUserid,userPhoto.getUserid());
        lambdaQueryWrapper.eq(UserPhoto::getPhotoid,userPhoto.getPhotoid());
        userPhotoMapper.delete(lambdaQueryWrapper);
        return Result.ok(null);
    }
}




