package com.nchu.controller;

import com.nchu.feign.utils.Result;
import com.nchu.pojo.ArticlePhoto;
import com.nchu.service.ArticlePhotoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("articlePhoto")
public class ArticlePhotoController {
    @Autowired
    private ArticlePhotoService articlePhotoService;
    @GetMapping("{articleid}")
    public Result get(@PathVariable(name = "articleid") int articleid){
        return articlePhotoService.get(articleid);
    }
    @GetMapping("/list/{articleid}")
    public Result get2(@PathVariable(name = "articleid") int articleid){
        return articlePhotoService.get2(articleid);
    }
    @PostMapping()
    public Result add(@RequestBody ArticlePhoto articlePhoto){
        return articlePhotoService.add(articlePhoto);
    }
    @DeleteMapping("{articleid}")
    public Result delete(@PathVariable(name = "articleid")int articleid){
        return articlePhotoService.delete(articleid);
    }
}
