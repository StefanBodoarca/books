package com.ro;

import com.ro.config.ProjectConfig;
import com.ro.service.CommentService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {
    public static void main(String[] args) {
        var context = new AnnotationConfigApplicationContext(ProjectConfig.class);

        var cs1 = context.getBean("commentService", CommentService.class);
        var cs2 = context.getBean("commentService", CommentService.class);

        boolean b1 = cs1 == cs2;

        //this prints false as the bean instances are different
        System.out.println(b1);
    }
}