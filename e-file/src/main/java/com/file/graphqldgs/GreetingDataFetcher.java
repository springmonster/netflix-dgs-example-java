package com.file.graphqldgs;

import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsQuery;

@DgsComponent
public class GreetingDataFetcher {

    @DgsQuery
    public String greeting() {
        return "hello world!";
    }
}
