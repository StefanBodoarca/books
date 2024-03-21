package com.ro.chapter2.repositories;

import com.ro.chapter2.Message;
import org.springframework.data.repository.CrudRepository;

public interface MessageRepository extends CrudRepository<Message, Long> {
}
