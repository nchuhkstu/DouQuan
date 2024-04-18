package com.nchu.controller;

import com.nchu.pojo.UserArticle;
import com.nchu.service.UserArticleService;
import com.nchu.feign.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("userArticle")
@CrossOrigin
public class UserArticleController {
    @Autowired
    private UserArticleService userArticleService;
    @GetMapping("{userid}/{pageSize}/{currentPage}")
    public Result getArticles(@PathVariable(name = "userid") int userid,
                              @PathVariable(name = "pageSize") int pageSize,
                              @PathVariable(name = "currentPage") int currentPage){
        return userArticleService.getArticles(userid, pageSize,currentPage);
    }
    @PostMapping()
    public Result addArticles(@RequestBody UserArticle userArticle){
        return userArticleService.addArticles(userArticle);
    }
    @DeleteMapping()
    public Result deleteArticles(@RequestBody UserArticle userArticle){
        return userArticleService.deleteArticles(userArticle);
    }
    @GetMapping("{articleid}")
    public Result getUser(@PathVariable(name = "articleid") int articleid){
        return userArticleService.getUser(articleid);
    }
}
