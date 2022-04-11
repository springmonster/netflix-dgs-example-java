package com.http.graphqldgs.context;

public class MyContext {
    private final String customState = "Custom state!";

    public String getCustomState() {
        return customState;
    }
}