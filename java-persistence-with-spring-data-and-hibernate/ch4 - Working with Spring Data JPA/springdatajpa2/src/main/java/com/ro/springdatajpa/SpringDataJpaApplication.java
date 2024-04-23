package com.ro.springdatajpa;

import com.ro.springdatajpa.entities.User;
import com.ro.springdatajpa.repositories.UserRepository;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;
import java.time.Month;

@SpringBootApplication
public class SpringDataJpaApplication {

	public static void main(String[] args) {
		/*
			SpringApplication.run will load the standalone Spring application from the
			main method. It will create an appropriate ApplicationContext instance and load beans.
		 */
		SpringApplication.run(SpringDataJpaApplication.class, args);
	}
}
