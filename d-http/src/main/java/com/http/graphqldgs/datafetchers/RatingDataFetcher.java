package com.http.graphqldgs.datafetchers;

import com.http.graphqldgs.types.Rating;
import com.http.graphqldgs.types.RatingInput;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsMutation;
import com.netflix.graphql.dgs.InputArgument;

@DgsComponent
public class RatingDataFetcher {

    @DgsMutation
    public Rating addRating(String title, int stars) {
        Rating rating = new Rating();
        rating.setAvgStars(Double.valueOf(stars));
        return rating;
    }

    @DgsMutation
    public Rating addRatingWithInput(@InputArgument(name = "input") RatingInput ratingInput) {
        return new Rating(Double.valueOf(ratingInput.getStars()));
    }
}
