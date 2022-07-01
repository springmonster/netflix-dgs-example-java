package com.interfaceunion.datafetcher;

import com.codegen.graphqldgs.types.ActionMovie;
import com.codegen.graphqldgs.types.Movie;
import com.codegen.graphqldgs.types.ScaryMovie;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsData;
import graphql.com.google.common.collect.Lists;

import java.util.List;

@DgsComponent
public class MovieDataFetcher {

    @DgsData(parentType = "Query", field = "movies")
    public List<Movie> movies() {
        return Lists.newArrayList(
                new ActionMovie("Crouching Tiger", 0),
                new ActionMovie("Black hawk down", 10),
                new ScaryMovie("American Horror Story", true, 10),
                new ScaryMovie("Love Death + Robots", false, 4)
            );
    }
}
