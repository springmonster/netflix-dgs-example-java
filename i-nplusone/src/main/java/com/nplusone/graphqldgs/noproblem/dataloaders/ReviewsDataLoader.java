package com.nplusone.graphqldgs.noproblem.dataloaders;

import com.inplusone.graphqldgs.types.ReviewN;
import com.netflix.graphql.dgs.DgsDataLoader;
import com.nplusone.graphqldgs.noproblem.services.DefaultReviewsService;
import org.dataloader.MappedBatchLoader;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

@DgsDataLoader(name = "reviews")
public class ReviewsDataLoader implements MappedBatchLoader<Integer, List<ReviewN>> {
    private final DefaultReviewsService reviewsService;
    
    public ReviewsDataLoader(DefaultReviewsService reviewsService) {
        this.reviewsService = reviewsService;
    }
    
    /**
     * This method will be called once, even if multiple datafetchers use the load() method on the DataLoader.
     * This way reviews can be loaded for all the Shows in a single call instead of per individual Show.
     */
    @Override
    public CompletionStage<Map<Integer, List<ReviewN>>> load(Set<Integer> keys) {
        
        return CompletableFuture.supplyAsync(() -> reviewsService.reviewsForShows(new ArrayList<>(keys)));
    }
}
