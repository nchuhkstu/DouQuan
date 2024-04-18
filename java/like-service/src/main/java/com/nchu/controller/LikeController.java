package com.nchu.controller;

import com.nchu.feign.utils.Result;
import com.nchu.pojo.Like;
import com.nchu.service.LikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("like")
public class LikeController {
    @Autowired
    private LikeService likeService;
    @GetMapping("{likeid}")
    public Result get(@PathVariable(name = "likeid")int likeid){
        return likeService.get(likeid);
    }
    @PostMapping()
    public Result add(@RequestBody Like like){
        return likeService.add(like);
    }
    @DeleteMapping("{likeid}")
    public Result delete(@PathVariable(name = "likeid")int likeid){
        return likeService.delete(likeid);
    }
    @GetMapping("user/{likeid}")
    public Result getUserid(@PathVariable(name = "likeid")int likeid){
        return likeService.getUserid(likeid);
    }
}
