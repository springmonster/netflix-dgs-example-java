package com.webflux.datafetcher;

import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsQuery;
import reactor.core.publisher.Mono;

@DgsComponent
public class SecureDataFetcher {

    @DgsQuery
    public Mono<String> secureNone() {
        return Mono.just("hello everyone!");
    }

    @DgsQuery
//    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public Mono<String> secureUser() {
        return Mono.just("hello user or admin!");
    }

    @DgsQuery
    public Mono<String> secureAdmin() {
        return Mono.just("hello admin!");
    }
}
