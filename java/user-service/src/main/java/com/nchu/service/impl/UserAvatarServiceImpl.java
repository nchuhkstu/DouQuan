package com.nchu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nchu.feign.utils.JwtHelper;
import com.nchu.feign.utils.Result;
import com.nchu.mapper.UserAvatarMapper;
import com.nchu.pojo.UserAvatar;
import com.nchu.service.UserAvatarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author qhl666
 * @description 针对表【user_Avatar】的数据库操作Service实现
 * @createDate 2024-04-10 11:00:45
 */
@Service
public class UserAvatarServiceImpl extends ServiceImpl<UserAvatarMapper, UserAvatar>
        implements UserAvatarService{
    @Autowired
    private UserAvatarMapper userAvatarMapper;
    @Autowired
    private JwtHelper jwtHelper;
    @Override
    public Result get(int userid) {
        LambdaQueryWrapper<UserAvatar> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(UserAvatar::getUserid,userid);
        List<UserAvatar> list = userAvatarMapper.selectList(lambdaQueryWrapper);
        List<Integer> list1 = list.stream().map(UserAvatar::getPhotoid).toList();
        return Result.ok(list1);
    }

    @Override
    public Result delete(UserAvatar userAvatar) {
        LambdaQueryWrapper<UserAvatar> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(UserAvatar::getUserid,userAvatar.getUserid());
        lambdaQueryWrapper.eq(UserAvatar::getPhotoid,userAvatar.getPhotoid());
        userAvatarMapper.delete(lambdaQueryWrapper);
        return Result.ok(null);
    }

    @Override
    public Result getDetail(int userid) {
        return null;
    }

    @Override
    public Result add(UserAvatar userAvatar, String token) {
        Long userid = jwtHelper.getUserId(token);
        userAvatar.setUserid(Math.toIntExact(userid));
        userAvatarMapper.insert(userAvatar);
        return Result.ok(null);
    }
}




