package com.nchu.controller;

import com.nchu.feign.utils.Result;
import com.nchu.pojo.ArticleView;
import com.nchu.service.ArticleViewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("articleView")
public class ArticleViewController {
    @Autowired
    private ArticleViewService articleViewService;
    @GetMapping("{articleid}")
    public Result getCount(@PathVariable(name = "articleid") int articleid){
        return articleViewService.getCount(articleid);
    }
    @PostMapping()
    public Result addView(@RequestBody ArticleView articleView){
        return articleViewService.addView(articleView);
    }
    @DeleteMapping()
    public Result deleteView(@RequestBody ArticleView articleView){
        return articleViewService.deleteView(articleView);
    }
}
