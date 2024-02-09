package com.ro;

import com.ro.config.ProjectConfig;
import com.ro.model.Comment;
import com.ro.processor.CommentProcessor;
import com.ro.service.CommentService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {
    public static void main(String[] args) {
        var context = new AnnotationConfigApplicationContext(ProjectConfig.class);
        System.out.println("Context in main: " + context);

        var commentService = context.getBean(CommentService.class);

        Comment c1 = new Comment("ab");
        Comment c2 = new Comment("I agree with that!");

        commentService.sendComment(c1);
        System.out.println("\n\n");
        commentService.sendComment(c2);

        commentService.checkCommentServiceBeanExistence();

        System.out.println("\nOn Main\n");
        if (context.containsBean("commentService")) {
            System.out.println("Injected context contains commentService");
        }

        if (context.containsBean("commentProcessor")) {
            System.out.println("CommentProcessor contained in injected bean: " + context.getBean(CommentProcessor.class));
        }

    }
}