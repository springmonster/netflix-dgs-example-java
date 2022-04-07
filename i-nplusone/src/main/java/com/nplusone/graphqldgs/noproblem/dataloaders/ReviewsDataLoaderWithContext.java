package com.nplusone.graphqldgs.noproblem.dataloaders;

import com.inplusone.graphqldgs.types.ReviewN;
import com.netflix.graphql.dgs.DgsDataLoader;
import com.nplusone.graphqldgs.noproblem.services.DefaultReviewsService;
import org.dataloader.BatchLoaderEnvironment;
import org.dataloader.MappedBatchLoaderWithContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

@DgsDataLoader(name = "reviewsWithContext")
public class ReviewsDataLoaderWithContext implements MappedBatchLoaderWithContext<Integer, List<ReviewN>> {
    private final static Logger logger = LoggerFactory.getLogger(ReviewsDataLoaderWithContext.class);

    private final DefaultReviewsService reviewsService;

    @Autowired
    public ReviewsDataLoaderWithContext(DefaultReviewsService reviewsService) {
        this.reviewsService = reviewsService;
    }

    @Override
    public CompletionStage<Map<Integer, List<ReviewN>>> load(Set<Integer> keys, BatchLoaderEnvironment environment) {
        logger.info("3 ------> load");
        return CompletableFuture.supplyAsync(() -> reviewsService.reviewsForShows(new ArrayList<>(keys)));
    }

}
