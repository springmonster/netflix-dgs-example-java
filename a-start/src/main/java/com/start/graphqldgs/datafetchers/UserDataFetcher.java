package com.start.graphqldgs.datafetchers;

import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsQuery;
import com.start.graphqldgs.types.User;

@DgsComponent
public class UserDataFetcher {
    @DgsQuery
    public User user() {
        return new User("id1", "zhangsan");
    }
}
