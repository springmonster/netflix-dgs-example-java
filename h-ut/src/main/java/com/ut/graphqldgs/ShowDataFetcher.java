package com.ut.graphqldgs;

import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsMutation;
import com.netflix.graphql.dgs.DgsQuery;
import com.netflix.graphql.dgs.InputArgument;
import com.ut.graphqldgs.types.Show;
import com.ut.graphqldgs.types.ShowInput;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@DgsComponent
public class ShowDataFetcher {

    private final ShowsService showsService;

    public ShowDataFetcher(ShowsService showsService) {
        this.showsService = showsService;
    }

    @DgsQuery
    public String greeting() {
        return "greeting!";
    }

    @DgsQuery
    public List<Show> shows(@InputArgument String titleFilter) {
        if (titleFilter == null) {
            return showsService.shows();
        }

        return showsService.shows().stream().filter(s -> s.getTitle().contains(titleFilter)).collect(Collectors.toList());
    }

    @DgsMutation
    public Show addShow(@InputArgument(name = "input") ShowInput input) {
        return new Show(new Random().nextInt(), input.getTitle(), input.getReleaseYear(), new BigDecimal(100), Instant.now(), 100L);
    }
}
