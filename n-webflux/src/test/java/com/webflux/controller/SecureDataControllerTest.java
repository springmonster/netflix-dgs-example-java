package com.webflux.controller;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
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

    @Disabled
    @Test
    @WithMockUser(username = "Alice", password = "Alice")
    void secureUserWithWrongUser() {
        rest.get().uri("/secureUser")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isUnauthorized();
    }

    @Test
    @WithMockUser(username = "user", password = "user")
    void secureUserWithUser() {
        rest.get().uri("/secureUser")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectBody(String.class).isEqualTo("hello user or admin!");
    }

    @Disabled
    @Test
    void secureAdminWithNone() {
        rest.get().uri("/secureAdmin")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isUnauthorized();
    }

    @Disabled
    @Test
    @WithMockUser(username = "Alice", password = "Alice")
    void secureAdminWithWrongUser() {
        rest.get().uri("/secureAdmin")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isUnauthorized();
    }

    @Test
    @WithMockUser(username = "admin", password = "admin")
    void secureAdminWithAdmin() {
        rest.get().uri("/secureAdmin")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectBody(String.class).isEqualTo("hello admin!");
    }
}
