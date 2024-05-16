package com.ro.springdatajpa;

import com.ro.springdatajpa.entities.User;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.JpaSort;

import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class QueryResultsTest extends SpringDataJpaApplicationTests {

    @Test
    void testStreamable() {
        try(Stream<User> result =
                    /*
                        The test will call the findByEmailContaining method, searching for emails containing “somedomain.”
                     */
                    userRepository.findByEmailContaining("somedomain")
                            /*
                                The test will concatenate the resulting Streamable with the Streamable providing
                                the users of level 2. After the concatenation will result a stream of size 11, john2 user appearing 2 times.
                             */
                            .and(userRepository.findByUsername("john2"))
                            /*
                                The stream is
                                given as a resource of the try block, so it will automatically be closed. An alternative
                                is to explicitly call the close() method. Otherwise, the stream would keep the
                                underlying connection to the database.
                             */
                            .stream()) {
            assertEquals(11, result.count());
        }
    }

    @Test
    void findNumberOfUsersByActivity() {
        boolean active = false;

        assertEquals(0, userRepository.findNumberOfUsersByActivity(active));

        active = true;

        assertEquals(10, userRepository.findNumberOfUsersByActivityNative(active));
    }

    @Test
    void findByLevelAndActive() {
        boolean active = false;
        final int level = 1;

        assertEquals(0, userRepository.findByLevelAndActive(level, active).size());

        active = true;

        assertEquals(1, userRepository.findByLevelAndActive(level, active).size());
    }

    @Test
    void testFindByAsArrayAndSort() {
        List<Object[]> usersList1 = userRepository.findByAsArrayAndSort("n1", Sort.by("username"));
        List<Object[]> usersList2 = userRepository.findByAsArrayAndSort("n1", Sort.by("email_length").descending());

        /*
            JpaSort is a class that extends Sort, and it can use something other than property references and aliases for
            sorting. The unsafe property handling means that the provided String is not necessarily
            a property or an alias but can be an arbitrary expression inside the query.
         */
        List<Object[]> usersList3 = userRepository.findByAsArrayAndSort("jo", JpaSort.unsafe("LENGTH(u.email)").descending());

        assertAll(
                () -> assertEquals(2, usersList1.size()),
                () -> assertEquals("john1", usersList1.get(0)[0]),
                () -> assertEquals(20, usersList1.get(0)[1]),
                () -> assertEquals(2, usersList2.size()),
                () -> assertEquals("john10", usersList2.get(0)[0]),
                () -> assertEquals(21, usersList2.get(0)[1]),
                () -> assertEquals(10, usersList3.size()),
                () -> assertEquals("john10", usersList3.get(0)[0]),
                () -> assertEquals(21, usersList3.get(0)[1])
        );
    }

}
