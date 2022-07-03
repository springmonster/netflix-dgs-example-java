package com.webflux.controller;

import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class SecureDataController {

    @GetMapping("/secureNone")
    public Mono<String> secureNone() {
        return Mono.just("hello everyone!");
    }

    @GetMapping("/secureUser")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public Mono<String> secureUser() {
        return Mono.just("hello user or admin!");
    }

    @GetMapping("/secureAdmin")
    @Secured({"ROLE_ADMIN"})
    public Mono<String> secureAdmin() {
        return Mono.just("hello admin!");
    }
}
