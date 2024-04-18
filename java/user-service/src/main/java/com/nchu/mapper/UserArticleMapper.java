package com.nchu.mapper;

import com.nchu.pojo.UserArticle;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @author qhl666
* @description 针对表【user_article】的数据库操作Mapper
* @createDate 2024-04-10 11:00:45
* @Entity com.nchu.pojo.UserArticle
*/
public interface UserArticleMapper extends BaseMapper<UserArticle> {
    List<Integer> queryByPage(@Param("userid") Integer userid);

}




