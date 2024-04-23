package com.ro.springdatajpa.repositories;

import com.ro.springdatajpa.entities.User;
import org.springframework.data.repository.CrudRepository;

/*
    We can directly call methods such as save, findAll, and findById, inherited from CrudRepository,
    and we can use them without any additional information to execute the usual operations against the
    database. Spring Data JPA will create a proxy class implementing the UserRepository
    interface and implement its methods.
 */
public interface UserRepository extends CrudRepository<User, Long> {
}
