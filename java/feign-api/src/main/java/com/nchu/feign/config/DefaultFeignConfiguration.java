package com.nchu.feign.config;

import feign.Logger;
import org.springframework.context.annotation.Bean;

public class DefaultFeignConfiguration {
    @Bean
    public Logger.Level loglevel(){
        return Logger.Level.BASIC;
    }
}
