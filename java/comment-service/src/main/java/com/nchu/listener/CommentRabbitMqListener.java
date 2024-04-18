package com.nchu.listener;

import com.nchu.service.CommentService;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CommentRabbitMqListener {
    @Autowired
    private CommentService commentService;
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(name = "articleCommentDeleteQueue"),
            exchange = @Exchange(name = "article",type = ExchangeTypes.TOPIC),
            key = "articleComment.delete"
    ))
    public void listenArticleCommentDelete(Integer commentid){
        commentService.delete(commentid);
    }
}
