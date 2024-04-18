package com.nchu.mapper;

import com.nchu.pojo.UserLogin;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @author qhl666
* @description 针对表【user_login】的数据库操作Mapper
* @createDate 2024-04-10 11:00:45
* @Entity com.nchu.pojo.UserLogin
*/
public interface UserLoginMapper extends BaseMapper<UserLogin> {

    List<UserLogin> queryByPage(@Param("userid") Integer userid);
}




