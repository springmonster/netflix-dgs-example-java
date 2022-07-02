package com.dynamicschema.datafetcher;

import com.dynamicschema.entity.User;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsMutation;

@DgsComponent
public class UserDataFetcher {

    @DgsMutation
    public User createUser(String username, String password) {
        return new User(username, password);
    }
}
