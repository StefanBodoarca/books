package com.ro;

import com.ro.config.ProjectConfig;
import com.ro.service.CommentService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {
    public static void main(String[] args) {
        var ctx = new AnnotationConfigApplicationContext(ProjectConfig.class);

        var commentService1 = ctx.getBean("commentService", CommentService.class);
        var commentService2 = ctx.getBean("commentService", CommentService.class);

        boolean b1 = commentService1 == commentService2;

        System.out.println(b1);
    }
}