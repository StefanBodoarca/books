package com.ro;

import com.ro.config.ProjectConfig;
import com.ro.model.Parrot;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {
    public static void main(String[] args) {
        var context = new AnnotationConfigApplicationContext(ProjectConfig.class);

        var p = context.getBean("parrot2", Parrot.class);
        System.out.println(p.getName());

        var pikiBean = context.getBean("piki", Parrot.class);
        System.out.println(pikiBean.getName());
    }
}