package com.codegen.graphqldgs.datafetchers;


public class User {
    private int minScore;

    public int getMinScore() {
        return minScore;
    }

    public void setMinScore(int minScore) {
        this.minScore = minScore;
    }

    public User(int minScore) {
        this.minScore = minScore;
    }

    public User() {
    }

    public Float height(int minScore) {
        if (1 == minScore) {
            return 1f;
        } else {
            return 2f;
        }
    }
}
