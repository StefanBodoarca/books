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

	/*
		Spring Boot will run the @Bean annotated method, returning an Application-Runner
		just before SpringApplication.run() finishes.
	 */
	@Bean
	public ApplicationRunner configure(UserRepository userRepository) {
		return env -> {
			User user1 = new User("beth", LocalDate.of(2020, Month.AUGUST, 3));
			User user2 = new User("mike", LocalDate.of(2020, Month.JANUARY, 18));

			userRepository.save(user1);
			userRepository.save(user2);

			userRepository.findAll().forEach(System.out::println);
		};
	}
}
