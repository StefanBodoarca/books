package com.ro.springdatajpa;

import com.ro.springdatajpa.entities.User;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class FindUsersSortingAndPagingTest extends SpringDataJpaApplicationTests {
    @Test
    void testOrder() {
        User user1 = userRepository.findFirstByOrderByUsernameAsc();
        User user2 = userRepository.findTopByOrderByRegistrationDateDesc();
        Page<User> userPage = userRepository.findAll(PageRequest.of(1, 3));
        List<User> users = userRepository.findFirst2ByLevel(2,
                Sort.by("registrationDate"));
        assertAll(
                () -> assertEquals("john1", user1.getUsername()),
                () -> assertEquals("john10", user2.getUsername()),
                () -> assertEquals(1, users.size()),
                () -> assertEquals(3, userPage.getSize()),
                () -> assertEquals("john2", users.get(0).getUsername())
        );
    }
    @Test
    void testFindByLevel() {
        Sort.TypedSort<User> user = Sort.sort(User.class);
        List<User> users = userRepository.findByLevel(3,
                user.by(User::getRegistrationDate).descending());
        assertAll(
                () -> assertEquals(1, users.size()),
                () -> assertEquals("john3", users.get(0).getUsername())
        );
    }

    @Test
    void testFindByActive() {
        List<User> users = userRepository.findByActive(true,
                PageRequest.of(1, 4, Sort.by("registrationDate")));
        assertAll(
                () -> assertEquals(4, users.size()),
                () -> assertEquals("john1", users.get(0).getUsername())
        );
    }
}
