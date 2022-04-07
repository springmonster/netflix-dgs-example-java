package com.nplusone.graphqldgs.noproblem.dataloader;

import com.inplusone.graphqldgs.types.ShowN;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsQuery;
import com.netflix.graphql.dgs.InputArgument;
import com.nplusone.graphqldgs.noproblem.services.ShowsNServiceImpl;

import java.util.List;
import java.util.stream.Collectors;

@DgsComponent
public class ShowsDatafetcher {
    private final ShowsNServiceImpl showsNService;
    
    public ShowsDatafetcher(ShowsNServiceImpl showsService) {
        this.showsNService = showsService;
    }
    
    /**
     * This datafetcher resolves the shows field on Query.
     * It uses an @InputArgument to get the titleFilter from the Query if one is defined.
     */
    @DgsQuery
    public List<ShowN> showsN(@InputArgument("titleFilter") String titleFilter) {
        if (titleFilter == null) {
            return showsNService.shows();
        }
        
        return showsNService.shows().stream().filter(s -> s.getTitle().contains(titleFilter)).collect(Collectors.toList());
    }
}
