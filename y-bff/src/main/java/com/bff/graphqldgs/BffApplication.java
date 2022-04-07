package com.bff.graphqldgs;

import com.netflix.graphql.dgs.client.HttpResponse;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@SpringBootApplication
public class BffApplication {
    public static void main(String[] args) {
        SpringApplication.run(BffApplication.class, args);
    }

    public static HttpResponse execute(String url, Map<String, ? extends List<String>> headers, String body) {
        /**
         * The requestHeaders providers headers typically required to call a GraphQL endpoint, including the Accept and Content-Type headers.
         * To use RestTemplate, the requestHeaders need to be transformed into Spring's HttpHeaders.
         */
        HttpHeaders requestHeaders = new HttpHeaders();
        headers.forEach(requestHeaders::put);

        /**
         * Use RestTemplate to call the GraphQL service.
         * The response type should simply be String, because the parsing will be done by the GraphQLClient.
         */
        var dgsRestTemplate = new RestTemplate();
        ResponseEntity<String> exchange = dgsRestTemplate.exchange(url, HttpMethod.POST, new HttpEntity(body, requestHeaders), String.class);

        /**
         * Return a HttpResponse, which contains the HTTP status code and response body (as a String).
         * The way to get these depend on the HTTP client.
         */
        return new HttpResponse(exchange.getStatusCodeValue(), exchange.getBody());
    }
}
