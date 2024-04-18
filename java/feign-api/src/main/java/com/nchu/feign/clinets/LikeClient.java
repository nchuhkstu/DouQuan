package com.nchu.feign.clinets;

import com.nchu.feign.pojo.Like;
import com.nchu.feign.utils.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(value = "likeservice")
public interface LikeClient {
    @DeleteMapping("like/{likeid}")
    Result delete(@PathVariable(name = "likeid")int likeid);

    @GetMapping("like/user/{likeid}")
    Result get(@PathVariable(name = "likeid")int likeid);

    @PostMapping("like")
    Result add(@RequestBody Like like);
}
