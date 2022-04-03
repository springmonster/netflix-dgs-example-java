package com.scalar.graphqldgs.datafetchers;

import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsQuery;
import com.netflix.graphql.dgs.InputArgument;
import com.scala.graphqldgs.types.Show;
import com.scalar.graphqldgs.services.ShowsService;

import java.util.List;
import java.util.stream.Collectors;

@DgsComponent
public class ShowsDatafetcher {
    private final ShowsService showsService;
    
    public ShowsDatafetcher(ShowsService showsService) {
        this.showsService = showsService;
    }
    
    /**
     * This datafetcher resolves the shows field on Query.
     * It uses an @InputArgument to get the titleFilter from the Query if one is defined.
     */
    @DgsQuery
    public List<Show> shows(@InputArgument("titleFilter") String titleFilter) {
        if (titleFilter == null) {
            return showsService.shows();
        }
        
        return showsService.shows().stream().filter(s -> s.getTitle().contains(titleFilter)).collect(Collectors.toList());
    }
}
