package com.nchu.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nchu.feign.clinets.PhotoClient;
import com.nchu.feign.utils.JwtHelper;
import com.nchu.feign.utils.Result;
import com.nchu.feign.utils.ResultCodeEnum;
import com.nchu.pojo.UserAccount;
import com.nchu.pojo.UserAvatar;
import com.nchu.pojo.UserExtend;
import com.nchu.service.UserAccountService;
import com.nchu.service.UserAvatarService;
import com.nchu.service.UserExtendService;
import com.nchu.mapper.UserExtendMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.HashMap;

/**
* @author qhl666
* @description 针对表【user_extend】的数据库操作Service实现
* @createDate 2024-04-10 11:00:45
*/
@Service
public class UserExtendServiceImpl extends ServiceImpl<UserExtendMapper, UserExtend>
    implements UserExtendService{
    @Autowired UserExtendMapper userExtendMapper;
    @Autowired
    private PhotoClient photoClient;
    @Autowired
    private JwtHelper jwtHelper;
    @Autowired
    private UserAvatarService userAvatarService;

    @Override
    public Result get(Long userid) {
        UserExtend userExtend = userExtendMapper.selectById(userid);
        return Result.ok(userExtend);
    }

    @Override
    public Result put(UserExtend userExtend,String token) {
        Long userid = jwtHelper.getUserId(token);
        userExtend.setUserid(Math.toIntExact(userid));
        userExtendMapper.updateById(userExtend);
        return Result.ok(null);
    }

    @Override
    public Result getDetail(int userid) {
        UserExtend userExtend = userExtendMapper.selectById(userid);
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("userid",userid);
        hashMap.put("avatarUrl",photoClient.get(userExtend.getAvatarid()).getData());
        hashMap.put("name",userExtend.getName());
        return Result.ok(hashMap);
    }

    @Override
    public Result add(UserExtend userExtend) {
        userExtendMapper.insert(userExtend);
        return Result.ok(null);
    }

    @Override
    public Result uploadAvatar(int photoid, String token) {
        UserExtend userExtend = userExtendMapper.selectById(jwtHelper.getUserId(token));
        userExtend.setAvatarid(photoid);
        userExtendMapper.updateById(userExtend);
        UserAvatar userAvatar = new UserAvatar(photoid, Math.toIntExact(jwtHelper.getUserId(token)));
        userAvatarService.add(userAvatar,token);
        return Result.ok(null);
    }

}




