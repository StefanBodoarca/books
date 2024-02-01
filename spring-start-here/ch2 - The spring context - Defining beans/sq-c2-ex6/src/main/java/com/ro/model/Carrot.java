package com.ro.model;

import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class Carrot {
    private String color;

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    @PostConstruct
    public void init() {
        this.color = "orange";
    }
}
