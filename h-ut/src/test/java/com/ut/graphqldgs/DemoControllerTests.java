package com.ut.graphqldgs;

import com.netflix.graphql.dgs.client.DefaultGraphQLClient;
import com.netflix.graphql.dgs.client.GraphQLClient;
import com.netflix.graphql.dgs.client.GraphQLResponse;
import com.netflix.graphql.dgs.client.HttpResponse;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class DemoControllerTests {

    private GraphQLClient client;
    private RestTemplate restTemplate;

    public DemoControllerTests(@LocalServerPort Integer port) {
        this.client = new DefaultGraphQLClient("http://localhost:" + port.toString() + "/graphql");
        this.restTemplate = new RestTemplate();
    }

    @Test
    void shows() {
        String query = "{ shows { title releaseYear }}";

        // Read more about executeQuery() at https://netflix.github.io/dgs/advanced/java-client/
        GraphQLResponse response =
                this.client.executeQuery(query, new HashMap<>(), (url, headers, body) -> {
                    HttpHeaders requestHeaders = new HttpHeaders();
                    headers.forEach(requestHeaders::put);
                    ResponseEntity<String> exchange =
                            this.restTemplate.exchange(url, HttpMethod.POST,
                                    new HttpEntity<String>(body, requestHeaders), String.class);
                    return new HttpResponse(exchange.getStatusCodeValue(), exchange.getBody());
                });

        List<?> titles = response.extractValueAsObject("shows[*].title", List.class);

        assertTrue(titles.contains("Ozark"));
    }
}