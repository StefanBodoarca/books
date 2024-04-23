package com.ro.springdatajpa.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
/*
    We specify USERS as the name of the corresponding table, because the default USER
    name is reserved in most database systems.
 */
@Table(name = "USERS")
@Data
/*
    Recall that JPA requires a constructor with no arguments for every persistent class.
    JPA uses the Java Reflection API on such a no-argument constructor to create instances.
 */
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue
    @Setter(AccessLevel.NONE)
    private Long id;

    private String username;
    private LocalDate registrationDate;

    public User(String username, LocalDate registrationDate) {
        this.username = username;
        this.registrationDate = registrationDate;
    }

    public User(String username) {
        this.username = username;
    }

}
