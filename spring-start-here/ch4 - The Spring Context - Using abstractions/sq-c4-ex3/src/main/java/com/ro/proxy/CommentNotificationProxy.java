package com.ro.proxy;

import com.ro.model.Comment;

public interface CommentNotificationProxy {
    void sendComment(Comment comment);
}
