package com.error.graphqldgs.datafetchers;

import com.error.graphqldgs.exception.RatingNotFoundException;
import com.error.graphqldgs.types.Rating;
import com.error.graphqldgs.types.RatingInput;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsMutation;
import com.netflix.graphql.dgs.DgsQuery;
import com.netflix.graphql.dgs.InputArgument;

@DgsComponent
public class RatingDataFetcher {

    @DgsQuery
    public Rating getRating(String id) {
        if ("1".equals(id)) {
            throw new RatingNotFoundException(id);
        } else {
            return null;
        }
    }

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
