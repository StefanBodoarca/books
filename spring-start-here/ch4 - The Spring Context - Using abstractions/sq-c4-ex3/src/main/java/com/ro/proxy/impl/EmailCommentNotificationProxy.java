package com.ro.proxy.impl;

import com.ro.model.Comment;
import com.ro.proxy.CommentNotificationProxy;
import org.springframework.stereotype.Component;

@Component
public class EmailCommentNotificationProxy implements CommentNotificationProxy {
    @Override
    public void sendComment(Comment comment) {
        System.out.println("Sending notification for comment " + comment.getText());
    }
}
