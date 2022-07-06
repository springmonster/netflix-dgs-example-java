package com.interfaceunion.datafetcher;

import com.netflix.graphql.dgs.DgsComponent;
import lombok.extern.slf4j.Slf4j;

@DgsComponent
@Slf4j
public class MovieDataFetcherWithByGetMergedField {

//    @DgsData(parentType = "Query", field = "movies")
//    public List<Movie> movies(DgsDataFetchingEnvironment environment) {
//        List<Movie> movies = new ArrayList<>();
//
//        environment.getMergedField().getSingleField().getSelectionSet().getSelections().forEach(selection -> {
//            InlineFragment inlineFragment = (InlineFragment) selection;
//            String name = inlineFragment.getTypeCondition().getName();
//            if ("ActionMovie".equals(name)) {
//                movies.addAll(fetchActionMovie());
//            }
//            if ("ScaryMovie".equals(name)) {
//                movies.addAll(fetchScaryMovie());
//            }
//        });
//
//        return movies;
//    }
//
//    private List<ActionMovie> fetchActionMovie() {
//        log.info("fetch action movie!");
//        return Lists.newArrayList(
//                new ActionMovie("Crouching Tiger", 0),
//                new ActionMovie("Black hawk down", 10)
//        );
//    }
//
//    private List<ScaryMovie> fetchScaryMovie() {
//        log.info("fetch scary movie!");
//        return Lists.newArrayList(
//                new ScaryMovie("American Horror Story", true, 10),
//                new ScaryMovie("Love Death + Robots", false, 4)
//        );
//    }
}
