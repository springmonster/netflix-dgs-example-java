package com.nplusone.graphqldgs.noproblem.services;

import com.github.javafaker.Faker;
import com.inplusone.graphqldgs.types.ReviewN;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import reactor.core.publisher.ConnectableFlux;
import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxSink;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * This service emulates a data store.
 * For convenience in the demo we just generate Reviews in memory, but imagine this would be backed by for example a database.
 * If this was indeed backed by a database, it would be very important to avoid the N+1 problem, which means we need to use a DataLoader to call this class.
 */
@Service
public class DefaultReviewsService {
    private final static Logger logger = LoggerFactory.getLogger(DefaultReviewsService.class);

    private final ShowsNServiceImpl showsService;
    private final Map<Integer, List<ReviewN>> reviews = new ConcurrentHashMap<>();
    private FluxSink<ReviewN> reviewsStream;
    private ConnectableFlux<ReviewN> reviewsPublisher;

    public DefaultReviewsService(ShowsNServiceImpl showsService) {
        this.showsService = showsService;
    }

    @PostConstruct
    private void createReviews() {
        Faker faker = new Faker();

        //For each show we generate a random set of reviews.
        showsService.shows().forEach(show -> {
            List<ReviewN> generatedReviews = IntStream.range(0, faker.number().numberBetween(1, 20)).mapToObj(number -> {
                LocalDateTime date = faker.date().past(300, TimeUnit.DAYS).toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
                return ReviewN.newBuilder().submittedDate(OffsetDateTime.of(date, ZoneOffset.UTC)).username(faker.name().username()).starScore(faker.number().numberBetween(0, 6)).build();
            }).collect(Collectors.toList());

            reviews.put(show.getId(), generatedReviews);
        });

        Flux<ReviewN> publisher = Flux.create(emitter -> {
            reviewsStream = emitter;
        });

        reviewsPublisher = publisher.publish();
        reviewsPublisher.connect();
    }
//
//    /**
//     * Hopefully nobody calls this for multiple shows within a single query, that would indicate the N+1 problem!
//     */
//    public List<ReviewN> reviewsForShow(Integer showId) {
//        return reviews.get(showId);
//    }

    /**
     * This is the method we want to call when loading reviews for multiple shows.
     * If this code was backed by a relational database, it would select reviews for all requested shows in a single SQL query.
     */
    public Map<Integer, List<ReviewN>> reviewsForShows(List<Integer> showIds) {
        logger.info("4 -------> Loading reviews for shows {}", showIds.stream().map(String::valueOf).collect(Collectors.joining(", ")));

        return reviews
                .entrySet()
                .stream()
                .filter(entry -> showIds.contains(entry.getKey())).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

//    public void saveReview(SubmittedReview reviewInput) {
//        List<ReviewN> reviewsForShow = reviews.computeIfAbsent(reviewInput.getShowId(), (key) -> new ArrayList<>());
//        ReviewN review = ReviewN.newBuilder()
//                .username(reviewInput.getUsername())
//                .starScore(reviewInput.getStarScore())
//                .submittedDate(OffsetDateTime.now()).build();
//
//        reviewsForShow.add(review);
//        reviewsStream.next(review);
//
//        logger.info("ReviewN added {}", review);
//    }
//
//    public void saveReviews(List<SubmittedReview> reviewsInput) {
//        reviewsInput.forEach(reviewInput -> {
//            List<ReviewN> reviewsForShow = reviews.computeIfAbsent(reviewInput.getShowId(), (key) -> new ArrayList<>());
//            ReviewN review = ReviewN.newBuilder()
//                    .username(reviewInput.getUsername())
//                    .starScore(reviewInput.getStarScore())
//                    .submittedDate(OffsetDateTime.now()).build();
//
//            reviewsForShow.add(review);
//            reviewsStream.next(review);
//
//            logger.info("ReviewN added {}", review);
//        });
//    }
//
//    public Publisher<ReviewN> getReviewsPublisher() {
//        return reviewsPublisher;
//    }
}
