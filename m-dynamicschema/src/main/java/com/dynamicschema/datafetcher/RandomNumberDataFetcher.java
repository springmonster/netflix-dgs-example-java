package com.dynamicschema.datafetcher;

import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsQuery;

import java.util.Random;

@DgsComponent
public class RandomNumberDataFetcher {

    @DgsQuery
    public Integer randomNumber(int bound) {
        return new Random().nextInt(bound);
    }
}
