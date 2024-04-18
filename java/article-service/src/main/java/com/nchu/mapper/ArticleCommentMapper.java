package com.nchu.mapper;

import com.nchu.pojo.ArticleComment;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @author qhl666
* @description 针对表【article_comment】的数据库操作Mapper
* @createDate 2024-04-10 18:52:21
* @Entity com.nchu.pojo.ArticleComment
*/
public interface ArticleCommentMapper extends BaseMapper<ArticleComment> {

    List<Integer> queryByPage(@Param("articleid") Integer articleid);
}




