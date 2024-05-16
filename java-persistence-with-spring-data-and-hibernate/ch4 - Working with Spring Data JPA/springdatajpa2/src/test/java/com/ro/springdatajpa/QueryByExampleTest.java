package com.ro.springdatajpa;

import com.ro.springdatajpa.entities.User;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class QueryByExampleTest extends SpringDataJpaApplicationTests {
    @Test
    void testEmailWithQueryByExample() {
        //This will represent the probe.
        User user = new User();
        user.setEmail("@somedomain.com1");

        ExampleMatcher matcher = ExampleMatcher.matching()
                /*
                    Any null reference property will be ignored by the matcher. However, we need to explicitly
                    ignore the level and active properties, which are primitives. If they were not
                    ignored, they would be included in the matcher with their default values (0 for
                    level and false for active) and would change the generated query.
                 */
                .withIgnorePaths("level", "active")
                .withMatcher("email", ExampleMatcher.GenericPropertyMatcher::endsWith);

        /*
            Create an Example that puts the probe and ExampleMatcher together and generates
            the query. The query will search for users that have an email property ending
            with the string defining the email of the probe.
         */
        Example<User> example = Example.of(user, matcher);
        List<User> users = userRepository.findAll(example);
        assertEquals(1, users.size());
    }

    @Test
    void testUsernameWithQueryByExample() {
        User user = new User();
        user.setUsername("J");
        ExampleMatcher matcher = ExampleMatcher.matching()
                .withIgnorePaths("level", "active")
                .withStringMatcher(ExampleMatcher.StringMatcher.STARTING)
                .withIgnoreCase();

        Example<User> example = Example.of(user, matcher);
        List<User> users = userRepository.findAll(example);
        assertEquals(10, users.size());
    }
}
