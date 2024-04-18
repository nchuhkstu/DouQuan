package com.nchu.feign.clinets;

import com.nchu.feign.pojo.Comment;
import com.nchu.feign.utils.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(value = "commentservice")
public interface CommentClient {
    @PostMapping("comment")
    Result add(@RequestBody Comment comment);
    @DeleteMapping("comment/{commentid}")
    Result delete(@PathVariable(name = "commentid")int commentid);
    @GetMapping("comment/{commentid}")
    Result get(@PathVariable(name = "commentid")int commentid);
}
