package com.ro.chapter2;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/*
    Every persistent entity class must have at least the @Entity annotation. Hibernate
    maps this class to a table called MESSAGE.
 */
@Entity
public class Message {

    /*
        Every persistent entity class must have an identifier attribute annotated with @Id.
        Hibernate maps this attribute to a column named id.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String text;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
