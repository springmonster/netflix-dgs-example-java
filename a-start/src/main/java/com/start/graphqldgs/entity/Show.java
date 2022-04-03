package com.start.graphqldgs.entity;

import java.util.List;

public class Show {
    private final String id;
    private final String title;
    private final Integer releaseYear;
    private final List<Actor> actors;

    public Show(String title, Integer releaseYear, String id, List<Actor> actors) {
        this.title = title;
        this.releaseYear = releaseYear;
        this.id = id;
        this.actors = actors;
    }

    public String getTitle() {
        return title;
    }

    public Integer getReleaseYear() {
        return releaseYear;
    }

    public String getId() {
        return id;
    }

    public List<Actor> getActors() {
        return actors;
    }
}
