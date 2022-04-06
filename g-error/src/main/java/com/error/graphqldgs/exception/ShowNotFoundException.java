package com.error.graphqldgs.exception;

public class ShowNotFoundException extends RuntimeException {
    public ShowNotFoundException(String id) {
        super("Show: " + id + " was not found.");
    }
}
