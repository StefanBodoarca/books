package com.ro.service;

import com.ro.model.Comment;
import com.ro.processor.CommentProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

@Service
public class CommentService {

    @Autowired
    private ApplicationContext context;

    public void sendComment(Comment comment) {
        System.out.println("Context in commentService: " + context);
        CommentProcessor commentProcessor = context.getBean(CommentProcessor.class);

        commentProcessor.setComment(comment);
        commentProcessor.processComment();
        commentProcessor.validateComment();
        commentProcessor.sendComment();
    }

    public void checkCommentServiceBeanExistence() {
        if (context.containsBean("commentService")) {
            System.out.println("Injected context contains commentService");
        }

        if (context.containsBean("commentProcessor")) {
            System.out.println("CommentProcessor contained in injected bean: " + context.getBean(CommentProcessor.class));
        }
    }

}
