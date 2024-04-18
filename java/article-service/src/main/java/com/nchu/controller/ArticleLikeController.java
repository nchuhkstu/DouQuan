package com.nchu.controller;

import com.nchu.feign.utils.Result;
import com.nchu.pojo.ArticleLike;
import com.nchu.service.ArticleLikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("articleLike")
public class ArticleLikeController {
    @Autowired
    private ArticleLikeService articleLikeService;
    @GetMapping("{articleid}")
    public Result getCount(@PathVariable(name = "articleid") int articleid){
        return articleLikeService.getCount(articleid);
    }
    @PostMapping("{articleid}")
    public Result addLike(@PathVariable(name = "articleid")int articleid,
                          @RequestHeader(name = "X-token")String token){
        return articleLikeService.addLike(articleid,token);
    }
    @DeleteMapping("{articleid}")
    public Result deleteLike(@RequestHeader(name = "X-token")String token,
                             @PathVariable(name = "articleid")int articleid){
        return articleLikeService.deleteLike(token,articleid);
    }
    @DeleteMapping("/delete/{articleid}")
    public Result delete(@PathVariable(name = "articleid")int articleid){
        return articleLikeService.delete(articleid);
    }
    @GetMapping("islike/{articleid}")
    public Result isLike(@PathVariable(name = "articleid")int articleid,
                         @RequestHeader(name = "X-token")String token){
        return articleLikeService.isLike(articleid,token);
    }
}
