package com.ut.graphqldgs;

import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsQuery;
import com.ut.graphqldgs.types.Pong;

@DgsComponent
public class PingDataFetcher {

    @DgsQuery
    public Pong ping() {
        return new Pong(1L);
    }
}
