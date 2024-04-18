package com.nchu.service;

import com.nchu.feign.utils.Result;
import com.nchu.pojo.ArticlePhoto;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author qhl666
* @description 针对表【article_photo】的数据库操作Service
* @createDate 2024-04-10 18:52:21
*/
public interface ArticlePhotoService extends IService<ArticlePhoto> {

    Result get(int articleid);

    Result add(ArticlePhoto articlePhoto);

    Result delete(int articleid);

    Result get2(int articleid);
}
