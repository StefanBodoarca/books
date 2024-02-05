package com.ro;

import com.ro.model.Comment;
import com.ro.proxy.impl.EmailCommentNotificationProxy;
import com.ro.repository.impl.DBCommentRepository;
import com.ro.service.CommentService;

public class Main {
    public static void main(String[] args) {
        var commentRepository = new DBCommentRepository();
        var commentNotificationProxy = new EmailCommentNotificationProxy();

        var commentService = new CommentService(commentRepository, commentNotificationProxy);

        var comment = new Comment();
        comment.setAuthor("Stefan");
        comment.setText("Demo comment");

        commentService.publishComment(comment);
    }
}