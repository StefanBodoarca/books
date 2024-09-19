package com.ro.repository;

import com.ro.example1.model.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface Item1Repository extends JpaRepository<Item, Long> {
}
