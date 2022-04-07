package com.nplusone.graphqldgs.noproblem.dataloader;

import com.inplusone.graphqldgs.DgsConstants;
import com.inplusone.graphqldgs.types.ReviewN;
import com.inplusone.graphqldgs.types.ShowN;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsData;
import com.netflix.graphql.dgs.DgsDataFetchingEnvironment;
import com.nplusone.graphqldgs.noproblem.dataloaders.ReviewsDataLoaderWithContext;
import com.nplusone.graphqldgs.noproblem.services.DefaultReviewsService;
import org.dataloader.DataLoader;

import java.util.List;
import java.util.concurrent.CompletableFuture;


@DgsComponent
public class ReviewsDataFetcher {

    private final DefaultReviewsService reviewsService;

    public ReviewsDataFetcher(DefaultReviewsService reviewsService) {
        this.reviewsService = reviewsService;
    }

    /**
     * This datafetcher will be called to resolve the "reviews" field on a ShowN.
     * It's invoked for each individual ShowN, so if we would load 10 shows, this method gets called 10 times.
     * To avoid the N+1 problem this datafetcher uses a DataLoader.
     * Although the DataLoader is called for each individual show ID, it will batch up the actual loading to a single method call to the "load" method in the ReviewsDataLoader.
     * For this to work correctly, the datafetcher needs to return a CompletableFuture.
     */
    @DgsData(parentType = DgsConstants.SHOWN.TYPE_NAME, field = DgsConstants.SHOWN.ReviewsN)
    public CompletableFuture<List<ReviewN>> reviewsN(DgsDataFetchingEnvironment dfe) {
        //Instead of loading a DataLoader by name, we can use the DgsDataFetchingEnvironment and pass in the DataLoader classname.
        DataLoader<Integer, List<ReviewN>> reviewsDataLoader = dfe.getDataLoader(ReviewsDataLoaderWithContext.class);

        //Because the reviews field is on ShowN, the getSource() method will return the ShowN instance.
        ShowN show = dfe.getSource();

        //Load the reviews from the DataLoader. This call is async and will be batched by the DataLoader mechanism.
        return reviewsDataLoader.load(show.getId());
    }

//    @DgsMutation
//    public List<ReviewN> addReview(@InputArgument SubmittedReview review) {
//        reviewsService.saveReview(review);
//
//        List<ReviewN> reviews = reviewsService.reviewsForShow(review.getShowId());
//
//        return Optional.ofNullable(reviews).orElse(Collections.emptyList());
//    }
//
//    @DgsMutation
//    public List<ReviewN> addReviews(@InputArgument(value = "reviews", collectionType = SubmittedReview.class) List<SubmittedReview> reviewsInput) {
//        reviewsService.saveReviews(reviewsInput);
//
//        List<Integer> showIds = reviewsInput.stream().map(SubmittedReview::getShowId).collect(Collectors.toList());
//        Map<Integer, List<ReviewN>> reviews = reviewsService.reviewsForShows(showIds);
//
//        return reviews.values().stream().flatMap(List::stream).collect(Collectors.toList());
//    }
//
//    @DgsSubscription
//    public Publisher<ReviewN> reviewAdded(@InputArgument Integer showId) {
//        return reviewsService.getReviewsPublisher();
//    }
}
