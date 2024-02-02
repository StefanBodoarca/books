package org.example;

import org.example.config.ProjectConfig;
import org.example.model.Parrot;
import org.example.model.Person;
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