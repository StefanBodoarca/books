package com.ro.processor;

import com.ro.model.Comment;
import com.ro.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class CommentProcessor {

    @Autowired
    private CommentRepository commentRepository;
    private Comment comment;

    public Comment getComment() {
        return comment;
    }

    public void setComment(Comment comment) {
        this.comment = comment;
    }

    public void processComment() {
        System.out.println("Processing the comment: " + comment);

        commentSanitization();

        comment.setProcessed(true);
    }

    public void validateComment() {
        System.out.println("Validating the comment: " + comment);

        comment.setValid(comment.getText().length() > 2);
    }

    public void sendComment() {
        if (!comment.isValid()) {
            System.out.println("Rejecting comment on processor: " + comment);
        } else {
            commentRepository.storeComment(comment);
        }
    }

    private void commentSanitization() {
        System.out.println("Sanitize comment: " + comment);
        System.out.println("""
                - check forbidden words
                - sanitize special character
                """);
    }


}
