package com.webflux.datafetcher;

import com.domain.graphqldgs.types.User;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsMutation;
import com.netflix.graphql.dgs.DgsQuery;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

@DgsComponent
public class UserDataFetcher {
    private List<User> users = new ArrayList<>() {
        {
            this.add(new User(1, "Alice", "Alice"));
            this.add(new User(2, "Bob", "Bob"));
        }
    };

    @DgsQuery
    public Mono<User> getUserById(int id) {
        User user = users.stream().filter(it -> it.getId().equals(id)).findFirst().get();
        return Mono.just(user);
    }

    @DgsQuery
    public Flux<User> getUsers() {
        return Flux.fromStream(users.stream());
    }

    @DgsMutation
    public Mono<User> createUser(String username, String password) {
        User user = new User(users.size() + 1, username, password);
        users.add(user);
        return Mono.just(user);
    }
}
