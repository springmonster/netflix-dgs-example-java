package com.start.graphqldgs.datafetchers;

import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsData;
import com.netflix.graphql.dgs.DgsQuery;
import com.netflix.graphql.dgs.InputArgument;
import com.start.graphqldgs.entity.Actor;
import com.start.graphqldgs.entity.Show;

import java.util.List;
import java.util.stream.Collectors;

@DgsComponent
public class ShowsDataFetcher {
    private final List<Actor> actors = List.of(
            new Actor("zhangsan"),
            new Actor("lisi")
    );

    private final List<Show> shows = List.of(
            new Show("Stranger Things", 2016, "1", actors
            ),
            new Show("Ozark", 2017, "2", null),
            new Show("The Crown", 2016, "3", null),
            new Show("Dead to Me", 2019, "4", null),
            new Show("Orange is the New Black", 2013, "5", null)
    );

    @DgsQuery
    public List<Show> shows(@InputArgument String titleFilter) {
        if (titleFilter == null) {
            return shows;
        }

        return shows.stream().filter(s -> s.getTitle().contains(titleFilter)).collect(Collectors.toList());
    }

    @DgsData(parentType = "Query", field = "showsWithDgsData")
    public List<Show> shows() {
        return shows;
    }
}
