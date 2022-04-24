package com.http.graphqldgs.datafetchers;

import com.http.graphqldgs.types.Stock;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsQuery;
import com.netflix.graphql.dgs.DgsSubscription;
import reactor.core.publisher.Flux;
import org.reactivestreams.Publisher;
import java.time.Duration;

@DgsComponent

public class SubscriptionDataFetcher {
    @DgsSubscription
    public Publisher<Stock> stocks() {
        return Flux.interval(Duration.ofSeconds(0), Duration.ofSeconds(1)).map(t -> new Stock("NFLX", ((Long)(500 + t)).doubleValue()));
    }
}