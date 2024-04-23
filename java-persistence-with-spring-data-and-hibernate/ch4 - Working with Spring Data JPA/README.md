## Chapter 4 - Working with Spring Data JPA

### Table of contents

### 4.1 Introducing Spring Data JPA

Spring Data JPA provides support for interacting with
JPA repositories. As you can see in figure 4.1, it is built
on top of the functionality offered by the Spring Data
Commons project and the JPA provider (Hibernate in
our case).

<img src="images/spring-data-jpa.png" width="250" height="250" alt="">\
(Credits: [Java Persistence with Spring Data and Hibernate](https://www.manning.com/books/java-persistence-with-spring-data-and-hibernate))

For the following examples we are using the CaveatEmptor model from chapter 3.

Check [springdatajpa app](springdatajpa) for following the first example.

NOTE: _CrudRepository_ is a generic technology-agnostic
persistence interface that we can use not only for JPA/relational databases but also for
NoSQL databases. For example, we can easily change the database from MySQL to
MongoDB without touching the implementation by changing the dependency from the
original spring-boot-starter-data-jpa to spring-boot-starter-data-mongodb.

Run [CH04.sql](springdatajpa/src/main/resources/CH04.sql) before running first example.\
Configure the mysql db as in [application.yml](springdatajpa/src/main/resources/application.yml).

First examples creates 2 users and saves them to the db and then retrieves them.\
Check [SpringDataJpaApplication.java](springdatajpa/src/main/java/com/ro/springdatajpa/SpringDataJpaApplication.java)
```java
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
```

Results:
- in db:\
  <img src="images/ex1-db.png" width="250" height="200" alt="">
- in logs:\
  <img src="images/ex1-logs.png" width="400" height="100" alt="">

### 4.4 Defining query methods with Spring Data JPA

Check [springdatajpa2 app](springdatajpa2) for following the second example.

We’ll extend the _User_ class by adding the fields _email_, _level_, and _active_.
A user may have different levels, which will allow them to execute particular actions (such as bidding
above some amount). A user may be active or may be retired (previously active in
the CaveatEmptor auction system, but not anymore). This is important information
that the CaveatEmptor application needs to keep about its users.

We’ll change the _UserRepository_ interface to extend _JpaRepository_ instead of _CrudRepository_. _JpaRepository_ extends _PagingAndSortingRepository_, 
which, in turn, extends _CrudRepository_.
_CrudRepository_ provides basic CRUD functionality, whereas _PagingAndSortingRepository_ offers convenient methods that sort and paginate the records. _JpaRepository_ offers JPA-related methods, such as **flushing
the persistence context and deleting records in a batch**. Additionally, _JpaRepository_
overwrites a few methods from _CrudRepository_, such as _findAll_, _findAllById_,
and _saveAll_ to return _List_ instead of _Iterable_.

A series of query methods were added to the [UserRepository](springdatajpa2/src/main/java/com/ro/springdatajpa/repositories/UserRepository.java) interface.
The purpose of these query methods is to retrieve information from the database.
Spring Data JPA provides a query builder mechanism that will create behavior for the
repository methods based on their names.

<img src="images/table-4.1.1.png" width="550" height="400" alt="">\
(Credits: [Java Persistence with Spring Data and Hibernate](https://www.manning.com/books/java-persistence-with-spring-data-and-hibernate))

<img src="images/table-4.1.2.png" width="550" height="400" alt="">\
(Credits: [Java Persistence with Spring Data and Hibernate](https://www.manning.com/books/java-persistence-with-spring-data-and-hibernate))

To test the methods that now belong to [UserRepository](springdatajpa2/src/main/java/com/ro/springdatajpa/repositories/UserRepository.java), 
we’ll create the [FindUsersUsingQueriesTest](springdatajpa2/src/test/java/com/ro/springdatajpa/FindUsersUsingQueriesTest.java) 
class and follow the same recipe for writing tests: call the repository
method and verify its results. We used the local db configuration [application.yml](springdatajpa2/src/main/resources/application.yml).