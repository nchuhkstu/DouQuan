package com.nchu;

import com.nchu.feign.clinets.*;
import com.nchu.feign.config.DefaultFeignConfiguration;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@MapperScan("com.nchu.mapper")
@EnableFeignClients(clients = {UserArticleClient.class, UserExtendClient.class, PhotoClient.class, LikeClient.class, CommentClient.class}, defaultConfiguration = DefaultFeignConfiguration.class)
public class articleApplication {
    public static void main(String[] args) {
        SpringApplication.run(articleApplication.class,args);
    }
}
