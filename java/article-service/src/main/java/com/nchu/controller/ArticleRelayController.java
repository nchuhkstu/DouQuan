package com.nchu.controller;

import com.nchu.feign.utils.Result;
import com.nchu.pojo.ArticleRelay;
import com.nchu.service.ArticleRelayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("articleRelay")
public class ArticleRelayController {
    @Autowired
    private ArticleRelayService articleRelayService;
    @GetMapping("{articleid}")
    public Result getCount(@PathVariable(name = "articleid") int articleid){
        return articleRelayService.getCount(articleid);
    }
    @PostMapping()
    public Result addRelay(@RequestBody ArticleRelay articleRelay){
        return articleRelayService.addRelay(articleRelay);
    }
    @DeleteMapping()
    public Result deleteRelay(@RequestBody ArticleRelay articleRelay){
        return articleRelayService.deleteRelay(articleRelay);
    }
    @DeleteMapping("{articleid}")
    public Result delete(@PathVariable(name = "articleid")int articleid){
        return articleRelayService.delete(articleid);
    }
}
