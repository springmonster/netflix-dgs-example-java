package com.http.graphqldgs.datafetchers;

import com.http.graphqldgs.types.Rating;
import com.http.graphqldgs.types.RatingInput;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsData;
import com.netflix.graphql.dgs.DgsMutation;
import com.netflix.graphql.dgs.InputArgument;
import graphql.schema.DataFetchingEnvironment;

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

    @DgsData(parentType = "Mutation", field = "addRatingWithDef")
    public Rating addRatingWithDef(DataFetchingEnvironment dataFetchingEnvironment) {
        int stars = dataFetchingEnvironment.getArgument("stars");

        if (stars < 1) {
            throw new IllegalArgumentException("Stars must be 1-5");
        }

        String title = dataFetchingEnvironment.getArgument("title");
        System.out.println("Rated " + title + " with " + stars + " stars");

        return new Rating(Double.parseDouble(String.valueOf(stars)));
    }
}
