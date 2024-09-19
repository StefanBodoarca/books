package com.ro.example1.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;

// The class maps by default to a table named ITEM in the database schema.
@Getter
@Entity
public class Item {

    @Id
    @GeneratedValue
    private Long id;
}
