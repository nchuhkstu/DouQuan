package com.nchu.controller;

import com.nchu.feign.utils.Result;
import com.nchu.pojo.Comment;
import com.nchu.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("comment")
public class CommentController {
    @Autowired
    private CommentService commentService;
    @PostMapping()
    public Result add(@RequestBody Comment comment){
        return commentService.add(comment);
    }
    @DeleteMapping("{commentid}")
    public Result delete(@PathVariable(name = "commentid")int commentid){
        return commentService.delete(commentid);
    }
    @GetMapping("{commentid}")
    public Result get(@PathVariable(name = "commentid")int commentid){
        return commentService.get(commentid);
    }
}
