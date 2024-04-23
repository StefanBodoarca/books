package com.ro.springdatajpa;

import com.ro.springdatajpa.entities.User;
import com.ro.springdatajpa.repositories.UserRepository;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

/*
	The @SpringBootTest annotation, added by Spring Boot to the initially created
	class, tells Spring Boot to search the main configuration class (the @SpringBoot-
	Application annotated class, for instance) and create the ApplicationContext to
	be used in the tests. Recall that the @SpringBootApplication annotation added by
	Spring Boot to the class containing the main method will enable the Spring Boot
	autoconfiguration mechanism, enable the scan on the package where the application
	is located, and allow the registration of extra beans in the context.
 */
@SpringBootTest

/*
	Using the @TestInstance(TestInstance.Lifecycle.PER_CLASS) annotation, we
	ask JUnit 5 to create a single instance of the test class and reuse it for all test methods.
	This will allow us to make the @BeforeAll and @AfterAll annotated methods
	non-static and to directly use the autowired UserRepository instance field inside
	them.
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
abstract class SpringDataJpaApplicationTests {
	@Autowired
	UserRepository userRepository;

	@BeforeAll
	void beforeAll() {
		userRepository.saveAll(generateUsers());
	}

	private static List<User> generateUsers() {
		List<User> users = new ArrayList<>();

		for (int i = 1; i <= 10; i++) {
			User j = new User("john" + i, LocalDate.of(2024, Month.APRIL, 13 + i));
			j.setEmail("john@somedomain.com" + i);
			j.setLevel(i);
			j.setActive(true);
			users.add(j);
		}

		return users;
	}

	@AfterAll
	void afterAll() {
		userRepository.deleteAll();
	}

}
