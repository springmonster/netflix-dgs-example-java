package com.webflux.datafetcher;

import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsQuery;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import reactor.core.publisher.Mono;

@DgsComponent
public class SecureDataFetcher {

    @DgsQuery
    public Mono<String> secureNone() {
        return Mono.just("hello everyone!");
    }

    @DgsQuery
    @PreAuthorize("hasRole('USER')")
    public Mono<String> secureUser() {
        return Mono.just("hello user or admin!");
    }

    @DgsQuery
    @Secured({"ROLE_ADMIN"})
    public Mono<String> secureAdmin() {
        return Mono.just("hello admin!");
    }
}
