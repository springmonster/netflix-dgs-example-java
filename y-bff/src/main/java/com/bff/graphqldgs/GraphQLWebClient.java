package com.bff.graphqldgs;

import com.netflix.graphql.dgs.client.MonoGraphQLClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.reactive.function.client.WebClient;

import javax.servlet.http.HttpServletRequest;

@Component
public class GraphQLWebClient {

    @Value("${graphQLServerUrl}")
    private String graphQLServerUrl;

    @Bean
    public MonoGraphQLClient monoGraphQLClient() {
        WebClient webClient = WebClient.create(graphQLServerUrl);
        return MonoGraphQLClient.createWithWebClient(webClient, httpHeaders -> {
            RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();

            if (requestAttributes != null) {
                HttpServletRequest request = (HttpServletRequest) requestAttributes.resolveReference(RequestAttributes.REFERENCE_REQUEST);
                String authValue = (request != null) ? request.getHeader("Authorization") : "";
                httpHeaders.add("Authorization", authValue);
            }
        });
    }
}
