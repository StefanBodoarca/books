package com.ro.springdatajpa.model;

import org.springframework.beans.factory.annotation.Value;

public class Projection {

    public interface UserSummary {

        //this method will return the username field
        /*
            If we include only methods such as getUsername(), we’ll create a closed projection — this is an interface whose getters
            all correspond to properties of the target entity. When you’re working with a closed
            projection, the query execution can be optimized by Spring Data JPA because all the
            properties needed by the projection proxy are known from the beginning.
         */
        String getUsername();

        //this method will return the concatenation of the username field, a space, and the email field
        /*
            If we include methods such as getInfo(), we create an open projection, which is more
            flexible. However, Spring Data JPA will not be able to optimize the query execution,
            because the SpEL expression is evaluated at runtime and may include any properties
            or combination of properties of the entity root.
         */
        @Value("#{target.username} #{target.email}")
        String getInfo();
    }

    public static class UsernameOnly {
        private String username;

        public UsernameOnly(String username) {
            this.username = username;
        }

        public String getUsername() {
            return username;
        }
    }
}
