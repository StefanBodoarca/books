package com.ro.model;

import java.util.StringTokenizer;

public class User1 {
    private String firstname;
    private String lastname;

    public String getName() {
        return firstname + " " + lastname;
    }

    public void setName(String name) {
        StringTokenizer tokenizer = new StringTokenizer(name);
        firstname = tokenizer.nextToken();
        lastname = tokenizer.nextToken();
    }
}
