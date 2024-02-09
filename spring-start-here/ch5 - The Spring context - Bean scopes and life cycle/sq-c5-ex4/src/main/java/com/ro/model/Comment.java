package com.ro.model;

public class Comment {
    private final String comment;
    private int numberOfCharacters;

    public Comment(String comment) {
        this.comment = comment;
    }
    private boolean valid = false;
    private boolean processed = false;

    public boolean isValid() {
        return valid;
    }

    public void setValid(boolean valid) {
        this.valid = valid;
    }

    public boolean isProcessed() {
        return processed;
    }

    public void setProcessed(boolean processed) {
        this.processed = processed;
    }

    public String getComment() {
        return comment;
    }

    public int getNumberOfCharacters() {
        return numberOfCharacters;
    }

    public void setNumberOfCharacters(int numberOfCharacters) {
        this.numberOfCharacters = numberOfCharacters;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "comment='" + comment + '\'' +
                '}';
    }
}
