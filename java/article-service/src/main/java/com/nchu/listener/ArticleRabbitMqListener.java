package com.nchu.listener;

import com.nchu.service.ArticleService;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ArticleRabbitMqListener {
    @Autowired
    private ArticleService articleService;
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(name = "UserArticleDeleteQueue"),
            exchange = @Exchange(name = "user",type = ExchangeTypes.TOPIC),
            key = "userArticle.delete"
    ))
    public void listenUserArticleDelete(Integer articleid){
        articleService.delete(articleid);
    }
}
