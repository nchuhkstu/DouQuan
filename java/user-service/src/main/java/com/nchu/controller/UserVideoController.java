package com.nchu.controller;

import com.nchu.pojo.UserVideo;
import com.nchu.service.UserVideoService;
import com.nchu.feign.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("userVideo")
public class UserVideoController {
    @Autowired
    private UserVideoService userVideoService;
    @GetMapping("{userid}/{pageSize}/{currentPage}")
    public Result getVideos(@PathVariable(name = "userid") int userid,
                            @PathVariable(name = "pageSize") int pageSize,
                            @PathVariable(name = "currentPage") int currentPage){
        return userVideoService.getVideos(userid,pageSize,currentPage);
    }
    @PostMapping()
    public Result addVideo(@RequestBody UserVideo userVideo){
        return userVideoService.addVideo(userVideo);
    }
    @DeleteMapping()
    public Result deleteVideo(@RequestBody UserVideo userVideo){
        return userVideoService.deleteVideo(userVideo);
    }
    @GetMapping("{videoid}")
    public Result get(@PathVariable(name = "videoid") int videoid){
        return userVideoService.get(videoid);
    }
}
