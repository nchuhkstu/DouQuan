package com.nchu.mapper;

import com.nchu.pojo.Article;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
* @author qhl666
* @description 针对表【article】的数据库操作Mapper
* @createDate 2024-04-10 18:52:21
* @Entity com.nchu.pojo.Article
*/
public interface ArticleMapper extends BaseMapper<Article> {

    List<Integer> findByPageHot();
}




