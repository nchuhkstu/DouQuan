package com.nchu.listener;

import com.nchu.service.LikeService;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class LikeRabbitMqListener {
    @Autowired
    private LikeService likeService;
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(name = "articleLikeDeleteQueue"),
            exchange = @Exchange(name = "article",type = ExchangeTypes.TOPIC),
            key = "articleLike.delete"
    ))
    public void listenArticleLikeDelete(Integer likeid){
        likeService.delete(likeid);
    }
}
