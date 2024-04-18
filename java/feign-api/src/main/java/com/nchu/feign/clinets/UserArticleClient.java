package com.nchu.feign.clinets;

import com.nchu.feign.pojo.UserArticle;
import com.nchu.feign.utils.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(value = "userservice", contextId = "article")
public interface UserArticleClient {
    @PostMapping("userArticle")
    Result addArticles(@RequestBody UserArticle userArticle);
    @GetMapping("userArticle/{articleid}")
    Result getUserid(@PathVariable(name = "articleid") int articleid);
}
