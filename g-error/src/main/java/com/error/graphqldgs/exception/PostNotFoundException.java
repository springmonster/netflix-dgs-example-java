package com.error.graphqldgs.exception;

public class PostNotFoundException extends RuntimeException {
    public PostNotFoundException(String id) {
        super("Post: " + id + " was not found.");
    }
}
