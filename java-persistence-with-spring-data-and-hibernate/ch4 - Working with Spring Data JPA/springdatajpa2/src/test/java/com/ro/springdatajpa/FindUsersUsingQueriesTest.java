package com.ro.springdatajpa;

import com.ro.springdatajpa.entities.User;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
public class FindUsersUsingQueriesTest extends SpringDataJpaApplicationTests {
    @Test
    void testFindAll() {
        List<User> users = userRepository.findAll();
        assertEquals(10, users.size());
    }
    @Test
    void testFindUser() {
        User beth = userRepository.findByUsername("john1");
        assertEquals("john1", beth.getUsername());
    }
    @Test
    void testFindAllByOrderByUsernameAsc() {
        List<User> users = userRepository.findAllByOrderByLevelAsc();
        assertAll(() -> assertEquals(10, users.size()),
                () -> assertEquals("john1", users.get(0).getUsername()),
                () -> assertEquals("john10",
                        users.get(users.size() - 1).getUsername()));
    }
    @Test
    void testFindByRegistrationDateBetween() {
        List<User> users = userRepository.findByRegistrationDateBetween(
                LocalDate.of(2024, Month.APRIL, 13),
                LocalDate.of(2024, Month.APRIL, 17));
        assertEquals(4, users.size());
    }
}
