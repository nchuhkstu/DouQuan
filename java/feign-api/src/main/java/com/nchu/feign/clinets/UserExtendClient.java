package com.nchu.feign.clinets;

import com.nchu.feign.utils.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "userservice", contextId = "extend")
public interface UserExtendClient {
    @GetMapping("userExtend/detail/{userid}")
    public Result getDetail(@PathVariable(name = "userid") int userid);
}
