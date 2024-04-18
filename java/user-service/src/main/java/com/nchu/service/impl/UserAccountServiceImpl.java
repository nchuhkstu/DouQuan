package com.nchu.service.impl;

import com.alibaba.druid.util.StringUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nchu.feign.utils.JwtHelper;
import com.nchu.feign.utils.MD5Util;
import com.nchu.feign.utils.Result;
import com.nchu.feign.utils.ResultCodeEnum;
import com.nchu.mapper.UserAccountMapper;
import com.nchu.pojo.UserAccount;
import com.nchu.pojo.UserExtend;
import com.nchu.pojo.UserLogin;
import com.nchu.service.UserAccountService;
import com.nchu.service.UserExtendService;
import com.nchu.service.UserLoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.HashMap;

/**
* @author qhl666
* @description 针对表【user_account】的数据库操作Service实现
* @createDate 2024-04-10 11:00:45
*/
@Service
public class UserAccountServiceImpl extends ServiceImpl<UserAccountMapper, UserAccount>
    implements UserAccountService{
    @Autowired
    private UserAccountMapper userAccountMapper;
    @Autowired
    private JwtHelper jwtHelper;
    @Autowired
    private UserLoginService userLoginService;
    @Autowired
    private UserExtendService userExtendService;

    @Override
    public Result login(UserAccount userAccount) {
        LambdaQueryWrapper<UserAccount> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(UserAccount::getName,userAccount.getName());
        UserAccount loginUserAccount = userAccountMapper.selectOne(lambdaQueryWrapper);
        if(loginUserAccount==null){
            return Result.build(null, ResultCodeEnum.USERNAME_ERROR);
        }
        if (!StringUtils.isEmpty(userAccount.getPassword()) && userAccount.getPassword().equals(loginUserAccount.getPassword())) {
            String token = jwtHelper.createToken(Long.valueOf(loginUserAccount.getUserid()));
            UserLogin userLogin = new UserLogin(loginUserAccount.getUserid(),new Timestamp(System.currentTimeMillis()),null);
            userLoginService.add(userLogin);
            return Result.ok(token);
        }
        return Result.build(null,ResultCodeEnum.PASSWORD_ERROR);
    }

    @Override
    public Result register(UserAccount userAccount) {
        LambdaQueryWrapper<UserAccount> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(UserAccount::getName,userAccount.getName());
        Long count = userAccountMapper.selectCount(lambdaQueryWrapper);
        if(count != 0){
            return Result.build(null,ResultCodeEnum.USERNAME_USED);
        }
        userAccount.setPassword(MD5Util.encrypt(userAccount.getPassword()));
        userAccountMapper.insert(userAccount);
        UserExtend userExtend = new UserExtend(userAccount.getUserid(),1,null,null,
                new Timestamp(System.currentTimeMillis()),"这个人很懒，什么都没有留下","默认昵称");
        userExtendService.add(userExtend);
        return Result.ok(null);
    }

    @Override
    public Result isLogin(String token) {
        if(jwtHelper.isExpiration(token))
            return Result.build(null, ResultCodeEnum.NOTLOGIN);
        int userId = jwtHelper.getUserId(token).intValue();
        return Result.ok(userId);
    }

    @Override
    public Result getUserid(String token) {
        Long userid = jwtHelper.getUserId(token);
        return Result.ok(userid);
    }

    @Override
    public Result getDetail(String token) {
        Long userid = jwtHelper.getUserId(token);
        return getDetail2(userid);
    }

    @Override
    public Result getDetail2(Long userid) {
        UserExtend userExtend = (UserExtend) userExtendService.get(userid).getData();
        Object userExtendDetail = userExtendService.getDetail(Math.toIntExact(userid)).getData();
        UserAccount userAccount = userAccountMapper.selectById(userid);
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("userExtendDetail",userExtendDetail);
        hashMap.put("name",userExtend.getName());
        hashMap.put("birthday",userExtend.getBirthday());
        hashMap.put("gender",userExtend.getGender());
        hashMap.put("registerTime",userExtend.getRegistrationtime());
        hashMap.put("selfIntroduction",userExtend.getSelfintroduction());
        hashMap.put("account",userAccount.getName());
        return Result.ok(hashMap);
    }
}




