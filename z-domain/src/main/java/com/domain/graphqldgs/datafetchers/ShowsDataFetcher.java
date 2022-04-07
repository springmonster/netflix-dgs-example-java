package com.domain.graphqldgs.datafetchers;

import com.domain.graphqldgs.types.Show;
import com.domain.graphqldgs.types.ShowInput;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsMutation;
import com.netflix.graphql.dgs.DgsQuery;
import com.netflix.graphql.dgs.InputArgument;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@DgsComponent
public class ShowsDataFetcher {
    private final List<Show> shows = List.of(
            new Show("Stranger Things", 2016, "1"
            ),
            new Show("Ozark", 2017, "2"),
            new Show("The Crown", 2016, "3"),
            new Show("Dead to Me", 2019, "4"),
            new Show("Orange is the New Black", 2013, "5")
    );


    @DgsQuery
    public List<Show> shows(@InputArgument String titleFilter) {
        if (titleFilter == null) {
            return shows;
        }

        return shows.stream().filter(s -> s.getTitle().contains(titleFilter)).collect(Collectors.toList());
    }

    @DgsMutation
    public Show addShow(@InputArgument ShowInput input) {
        return new Show(UUID.randomUUID().toString(), input.getReleaseYear(), input.getTitle());
    }
}
