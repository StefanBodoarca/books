package com.ro.controllers;

import com.ro.dto.Country;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CountryController {
    @GetMapping("/france")
    public Country france() {
        return Country.of("France", 67);
    }

    @GetMapping("/all")
    public List<Country> countries() {
        return List.of(
                Country.of("Spain", 47),
                Country.of("Germany", 55)
        );
    }
}
