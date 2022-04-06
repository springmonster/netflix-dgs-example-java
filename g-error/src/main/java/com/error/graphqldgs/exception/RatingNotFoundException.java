package com.error.graphqldgs.exception;

public class RatingNotFoundException extends RuntimeException {
    public RatingNotFoundException(String id) {
        super("Rating: " + id + " was not found.");
    }
}
