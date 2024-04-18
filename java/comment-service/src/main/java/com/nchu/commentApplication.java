package com.nchu;

import com.nchu.feign.clinets.UserExtendClient;
import com.nchu.feign.config.DefaultFeignConfiguration;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@MapperScan("com.nchu.mapper")
@EnableFeignClients(clients = {UserExtendClient.class}, defaultConfiguration = DefaultFeignConfiguration.class)
public class commentApplication {
    public static void main(String[] args) {
        SpringApplication.run(commentApplication.class,args);
    }
}
