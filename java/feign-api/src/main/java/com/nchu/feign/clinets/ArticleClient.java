package com.nchu.feign.clinets;

import com.nchu.feign.utils.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "articleservice")
public interface ArticleClient {
    @GetMapping("article/detail/{articleid}")
    Result getDetail(@PathVariable(name = "articleid")int articleid);
    @DeleteMapping("article/{articleid}")
    Result delete(@PathVariable(name = "articleid")int articleid);
}
