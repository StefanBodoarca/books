package com.ro.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;

import java.math.BigDecimal;

@Getter
@Setter
public class Account {

    @Id //We annotate the attribute that model the primary key with the @Id annotation
    private long id;
    private String name;
    private BigDecimal amount;
}
