package com.ro;

import com.ro.config.ProjectConfig;
import com.ro.model.Parrot;
import com.ro.model.Person;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {
    public static void main(String[] args) {

        var context = new AnnotationConfigApplicationContext(ProjectConfig.class);

        Person person = context.getBean(Person.class);
        Parrot parrot = context.getBean(Parrot.class);

        System.out.println(person);
        System.out.println(parrot);
    }
}