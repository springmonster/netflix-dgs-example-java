package com.ut.graphqldgs;

import com.netflix.graphql.dgs.client.GraphQLResponse;
import com.netflix.graphql.dgs.client.MonoGraphQLClient;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class DemoControllerTests {
    private MonoGraphQLClient monoGraphQLClient;

    public DemoControllerTests(@LocalServerPort Integer port) {
        WebClient webClient = WebClient.create("http://localhost:" + port.toString() + "/graphql");
        monoGraphQLClient = MonoGraphQLClient.createWithWebClient(webClient);
    }

    @Test
    void shows() {
        String query = "{ shows { title releaseYear }}";

        // Read more about executeQuery() at https://netflix.github.io/dgs/advanced/java-client/
        GraphQLResponse response =
                monoGraphQLClient.reactiveExecuteQuery(query).block();

        List<?> titles = response.extractValueAsObject("shows[*].title", List.class);

        assertTrue(titles.contains("Ozark"));
    }
}
