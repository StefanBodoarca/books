package com.ro;

import com.ro.config.ProjectConfig;
import com.ro.model.Carrot;
import com.ro.model.Parrot;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {
    public static void main(String[] args) {
        var context = new AnnotationConfigApplicationContext(ProjectConfig.class);

        var p = context.getBean(Parrot.class);
        System.out.println(p);
        System.out.println(p.getName());

        var c = context.getBean(Carrot.class);
        System.out.println(c);
        System.out.println(c.getColor());
    }
}