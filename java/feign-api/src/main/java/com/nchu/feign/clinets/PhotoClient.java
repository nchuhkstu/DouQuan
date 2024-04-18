package com.nchu.feign.clinets;

import com.nchu.feign.utils.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "photoservice")
public interface PhotoClient {
    @GetMapping("photo/{photoid}")
    Result get(@PathVariable(name = "photoid")int photoid);
    @DeleteMapping("photo/{photoid}")
    Result delete(@PathVariable(name = "photoid")int photoid);
}
