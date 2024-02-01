package com.ro;

import com.ro.config.ProjectConfig;
import com.ro.model.Parrot;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.function.Supplier;

public class Main {
    public static void main(String[] args) {
        var context = new AnnotationConfigApplicationContext(ProjectConfig.class);

        Parrot p = new Parrot();
        p.setName("Kiki");

        Supplier<Parrot> parrotSupplier = () -> p;

        context.registerBean("parrot1", Parrot.class, parrotSupplier, bc -> bc.setPrimary(true)); //the last param is optional (varargs)

        Parrot pFromContext = context.getBean(Parrot.class);
        System.out.println(pFromContext.getName());
    }
}