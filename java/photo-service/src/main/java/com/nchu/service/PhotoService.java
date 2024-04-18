package com.nchu.service;

import com.nchu.feign.utils.Result;
import com.nchu.pojo.Photo;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.multipart.MultipartFile;

/**
* @author qhl666
* @description 针对表【photo】的数据库操作Service
* @createDate 2024-04-11 16:01:21
*/
public interface PhotoService extends IService<Photo> {

    Result get(int photoid);

    Result delete(int photoid);

    Result upload(String token,MultipartFile[] files);
}
