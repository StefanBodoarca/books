package com.ro.proxy.impl;

import com.ro.model.Comment;
import com.ro.proxy.CommentNotificationProxy;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Component
@Primary
public class CommentPushNotificationProxy implements CommentNotificationProxy {
    @Override
    public void sendComment(Comment comment) {
        System.out.println("Sending push notification for comment " + comment.getText());
    }
}
