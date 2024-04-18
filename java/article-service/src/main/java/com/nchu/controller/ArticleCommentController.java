package com.nchu.controller;

import com.nchu.feign.pojo.Comment;
import com.nchu.feign.utils.Result;
import com.nchu.pojo.ArticleComment;
import com.nchu.service.ArticleCommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("articleComment")
public class ArticleCommentController {
    @Autowired
    private ArticleCommentService articleCommentService;
    @GetMapping("{articleid}/{pageSize}/{currentPage}")
    public Result getArticleComments(@PathVariable(name = "articleid") int articleid,
                                     @PathVariable(name = "pageSize") int pageSize,
                                     @PathVariable(name = "currentPage") int currentPage){
        return articleCommentService.getArticleComments(articleid,pageSize,currentPage);
    }
    @PostMapping("{articleid}")
    public Result addArticleComment(@RequestBody Comment comment,
                                    @RequestHeader("X-token")String token,
                                    @PathVariable("articleid")int articleid){
        return articleCommentService.addArticleComment(comment,token,articleid);
    }
    @DeleteMapping("{commentid}")
    public Result deleteArticleComment(@PathVariable(name = "commentid")int commentid,
                                       @RequestHeader(name = "X-token")String token){
        return articleCommentService.deleteArticleComment(commentid,token);
    }
    @DeleteMapping("/delete/{articleid}")
    public Result delete(@PathVariable(name = "articleid")int articleid){
        return articleCommentService.delete(articleid);
    }
    @GetMapping("{articleid}")
    public Result getCount(@PathVariable(name = "artcileid")int articleid){
        return articleCommentService.getCount(articleid);
    }
}
