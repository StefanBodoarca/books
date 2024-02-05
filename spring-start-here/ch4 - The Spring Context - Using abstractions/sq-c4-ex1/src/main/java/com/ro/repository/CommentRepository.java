package com.ro.repository;

import com.ro.model.Comment;

public interface CommentRepository {
    void storeComment(Comment comment);
}
