package com.nchu.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.nchu.feign.utils.PageBean;
import com.nchu.feign.utils.Result;
import com.nchu.mapper.UserLoginMapper;
import com.nchu.pojo.UserLogin;
import com.nchu.service.UserLoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author qhl666
* @description 针对表【user_login】的数据库操作Service实现
* @createDate 2024-04-10 11:00:45
*/
@Service
public class UserLoginServiceImpl extends ServiceImpl<UserLoginMapper, UserLogin>
    implements UserLoginService{
    @Autowired
    private UserLoginMapper userLoginMapper;

    @Override
    public Result getLogins(int userid, int pageSize, int currentPage) {
        PageHelper.startPage(currentPage,pageSize);
        List<UserLogin> pageList = userLoginMapper.queryByPage(userid);
        PageInfo<UserLogin> pageInfo = new PageInfo<>(pageList);
        PageBean<UserLogin> pageBean = new PageBean<>(pageInfo.getPageNum(),pageInfo.getPageSize(),pageInfo.getTotal(),pageInfo.getList());
        return Result.ok(pageBean);
    }

    @Override
    public Result add(UserLogin userLogin) {
        userLoginMapper.insert(userLogin);
        return Result.ok(null);
    }
}




