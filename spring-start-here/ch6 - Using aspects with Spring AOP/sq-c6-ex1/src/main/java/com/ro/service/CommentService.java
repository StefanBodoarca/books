package com.ro.service;

import com.ro.model.Comment;
import org.springframework.stereotype.Service;

import java.util.logging.Logger;

@Service
public class CommentService {
    private final Logger logger = Logger.getLogger(CommentService.class.getName()); //the class name is the logger name; other name can be used

    public void publishComment(Comment comment) {
        logger.info("Publishing comment: " + comment.getText());
    }
}
