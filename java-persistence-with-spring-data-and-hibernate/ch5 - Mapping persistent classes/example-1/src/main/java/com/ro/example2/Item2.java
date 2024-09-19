package com.ro.example2;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

// The class maps by default to a table named ITEM in the database schema.
@Getter
@Setter
@Entity
public class Item2 {

    @Id
    @GeneratedValue(generator = "ID_GENERATOR")
    private Long id;

    private String name;
    private Date auctionEnd;
}
