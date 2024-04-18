package com.nchu.controller;

import com.nchu.feign.utils.Result;
import com.nchu.pojo.Article;
import com.nchu.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@CrossOrigin
@RequestMapping("article")
public class ArticleController {
    @Autowired
    private ArticleService articleService;
    @GetMapping("{articleid}")
    public Result get(@PathVariable(name = "articleid") int articleid){
        return articleService.get(articleid);
    }
    @PostMapping()
    public Result add(@RequestHeader("X-token") String token, @RequestBody Article article){
        return articleService.add(token,article);
    }
    @DeleteMapping("{articleid}")
    public Result delete(@PathVariable(name = "articleid") int articleid){
        return articleService.delete(articleid);
    }
    @GetMapping("detail/{articleid}")
    public Result getDetail(@PathVariable(name = "articleid") int articleid){
        return articleService.getDetail(articleid);
    }
    @GetMapping("{pageSize}/{currentPage}")
    public Result getHot(@PathVariable(name = "pageSize")int pageSize,
                         @PathVariable(name = "currentPage")int currentPage){
        return articleService.getHot(pageSize,currentPage);
    }
}
