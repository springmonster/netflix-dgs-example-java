package com.nplusone.graphqldgs.problem;

import com.inplusone.graphqldgs.types.Review;
import com.inplusone.graphqldgs.types.Show;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsData;
import com.netflix.graphql.dgs.DgsDataFetchingEnvironment;

import java.util.List;

@DgsComponent
public class ShowsServiceImpl {
    private final List<Review> reviews1 = List.of(
            new Review(1),
            new Review(1),
            new Review(1),
            new Review(1)
    );

    private final List<Review> reviews2 = List.of(
            new Review(2),
            new Review(2),
            new Review(2),
            new Review(2)
    );

    private final List<Review> reviews3 = List.of(
            new Review(3),
            new Review(3),
            new Review(3),
            new Review(3)
    );

    private final List<Review> reviews4 = List.of(
            new Review(4),
            new Review(4),
            new Review(4),
            new Review(4)
    );

    private final List<Review> reviews5 = List.of(
            new Review(5),
            new Review(5),
            new Review(5),
            new Review(5)
    );

    private final List<Show> shows = List.of(
            new Show("1", "Stranger Things", reviews1),
            new Show("2", "Ozark", reviews2),
            new Show("3", "The Crown", reviews3),
            new Show("4", "Dead to Me", reviews4),
            new Show("5", "Orange is the New Black", reviews5)
    );

    @DgsData(parentType = "Query", field = "shows")
    public List<Show> getShows() {
        return shows;
    }

    @DgsData(parentType = "Show", field = "reviews")
    public List<Review> getReviewsForShow(DgsDataFetchingEnvironment dfe) {
        Show show = dfe.getSource();
        return getReviewsForShow(show.getShowId());
    }

    private List<Review> getReviewsForShow(String showId) {
        return this.shows.get(Integer.parseInt(showId) - 1).getReviews();
    }
}
