package com.ro.springdatajpa;

import com.ro.springdatajpa.entities.User;
import com.ro.springdatajpa.model.Projection;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ProjectionTest extends SpringDataJpaApplicationTests {

    @Test
    void testProjectionUsername() {
        List<Projection.UsernameOnly> users = userRepository.findByEmail("john@somedomain.com1");
        assertAll(
                () -> assertEquals(1, users.size()),
                () -> assertEquals("john1", users.get(0).getUsername())
        );
    }

    @Test
    void testProjectionUserSummary() {
        List<Projection.UserSummary> users =
                userRepository.findByRegistrationDateAfter(LocalDate.of(2024, Month.APRIL, 22));
        assertAll(
                () -> assertEquals(1, users.size()),
                () -> assertEquals("john10", users.get(0).getUsername()),
                () -> assertEquals("john10 john@somedomain.com10",
                        users.get(0).getInfo())
        );
    }

    @Test
    void testDynamicProjection() {
        List<Projection.UsernameOnly> usernames = userRepository.findByEmail("john@somedomain.com2", Projection.UsernameOnly.class);
        List<User> users = userRepository.findByEmail("john@somedomain.com2", User.class);
        assertAll(
                () -> assertEquals(1, usernames.size()),
                () -> assertEquals("john2", usernames.get(0).getUsername()),
                () -> assertEquals(1, users.size()),
                () -> assertEquals("john2", users.get(0).getUsername())
        );
    }

}
