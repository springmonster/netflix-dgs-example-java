package com.webflux.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest
@AutoConfigureWebTestClient
class SecureDataControllerTest {

    @Autowired
    WebTestClient rest;

    @Test
    void secureNone() {
        rest.get().uri("/secureNone")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody(String.class).isEqualTo("hello everyone!");
    }

    @Test
    void secureUserWithNone() {
        rest.get().uri("/secureUser")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isUnauthorized();
    }

    @Test
    void secureUserWithWrongUser() {
        rest.get().uri("/secureUser")
                .accept(MediaType.APPLICATION_JSON)
                .headers(headers -> headers.setBasicAuth("Alice", "Alice"))
                .exchange()
                .expectStatus().isUnauthorized();
    }

    @Test
    void secureUserWithUser() {
        rest.get().uri("/secureUser")
                .accept(MediaType.APPLICATION_JSON)
                .headers(headers -> headers.setBasicAuth("user", "user"))
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectBody(String.class).isEqualTo("hello user or admin!");
    }

    @Test
    void secureUserWithAdmin() {
        rest.get().uri("/secureUser")
                .accept(MediaType.APPLICATION_JSON)
                .headers(headers -> headers.setBasicAuth("admin", "admin"))
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectBody(String.class).isEqualTo("hello user or admin!");
    }

    @Test
    void secureAdminWithNone() {
        rest.get().uri("/secureAdmin")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isUnauthorized();
    }

    @Test
    void secureAdminWithWrongUser() {
        rest.get().uri("/secureAdmin")
                .accept(MediaType.APPLICATION_JSON)
                .headers(headers -> headers.setBasicAuth("Alice", "Alice"))
                .exchange()
                .expectStatus().isUnauthorized();
    }

    @Test
    void secureAdminWithAdmin() {
        rest.get().uri("/secureAdmin")
                .accept(MediaType.APPLICATION_JSON)
                .headers(headers -> headers.setBasicAuth("admin", "admin"))
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectBody(String.class).isEqualTo("hello admin!");
    }
}
