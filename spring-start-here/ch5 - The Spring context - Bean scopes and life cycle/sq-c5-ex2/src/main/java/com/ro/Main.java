package com.ro;

import com.ro.config.ProjectConfig;
import com.ro.service.CommentService;
import com.ro.service.UserService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {
    public static void main(String[] args) {
        var ctx = new AnnotationConfigApplicationContext(ProjectConfig.class);

        var commentService = ctx.getBean(CommentService.class);
        var userService = ctx.getBean(UserService.class);

        boolean b1 = commentService.getCommentRepository() == userService.getCommentRepository();

        System.out.println(b1);
    }
}