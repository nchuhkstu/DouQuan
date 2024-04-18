package com.nchu.listener;

import com.nchu.service.PhotoService;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PhotoRabbitMqListener {
    @Autowired
    private PhotoService photoService;
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(name = "articlePhotoDeleteQueue"),
            exchange = @Exchange(name = "article",type = ExchangeTypes.TOPIC),
            key = "articlePhoto.delete"
    ))
    public void listenArticlePhotoDeleteQueue(Integer photoid){
        photoService.delete(photoid);
    }
}
