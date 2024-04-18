package com.nchu.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nchu.feign.utils.JwtHelper;
import com.nchu.feign.utils.Result;
import com.nchu.feign.utils.ResultCodeEnum;
import com.nchu.pojo.Photo;
import com.nchu.service.PhotoService;
import com.nchu.mapper.PhotoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
* @author qhl666
* @description 针对表【photo】的数据库操作Service实现
* @createDate 2024-04-11 16:01:21
*/
@Service
public class PhotoServiceImpl extends ServiceImpl<PhotoMapper, Photo>
    implements PhotoService{
    @Autowired
    private PhotoMapper photoMapper;
    @Value("${upload.directory}")
    private String uploadDirectory;
    @Value("${image.position}")
    private String imagePosition;
    @Autowired
    private JwtHelper jwtHelper;

    @Override
    public Result get(int photoid) {
        String url = photoMapper.selectById(photoid).getUrl();
        return Result.ok(imagePosition + url);
    }

    @Override
    public Result delete(int photoid) {
        String url = photoMapper.selectById(photoid).getUrl();
        File file = new File(uploadDirectory + url);
        file.delete();
        photoMapper.deleteById(photoid);
        return Result.ok(null);
    }

    @Override
    public Result upload(String token,MultipartFile[] files) {
        if(jwtHelper.isExpiration(token))
            return Result.build(null, ResultCodeEnum.NOTLOGIN);
        List<Integer> fileList = new ArrayList<>();
        for (MultipartFile file : files) {
            try {
                long time = System.currentTimeMillis();
                Photo photo = new Photo();
                photo.setTime(new Timestamp(time));
                photo.setUrl(time + ".jpg");
                photoMapper.insert(photo);
                String filePath = uploadDirectory + time + ".jpg";
                file.transferTo(new File(filePath));
                fileList.add(photo.getPhotoid());
            } catch (IOException e) {
                e.printStackTrace();
                return Result.ok(ResultCodeEnum.ERROR);
            }
        }
        return Result.ok(fileList);
    }
}




