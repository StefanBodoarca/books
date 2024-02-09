package com.ro.repository;

import com.ro.model.Comment;
import org.springframework.stereotype.Repository;

@Repository
public class CommentRepository {

    public void storeComment(Comment c) {
        System.out.printf("Storing comment %s to DB!\n", c);
    }
}
