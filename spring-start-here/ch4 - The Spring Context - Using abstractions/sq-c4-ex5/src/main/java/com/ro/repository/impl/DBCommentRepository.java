package com.ro.repository.impl;

import com.ro.model.Comment;
import com.ro.repository.CommentRepository;
import org.springframework.stereotype.Repository;

@Repository
public class DBCommentRepository implements CommentRepository {
    @Override
    public void storeComment(Comment comment) {
        System.out.println("Storing comment " + comment.getText());
    }
}
