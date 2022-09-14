package com.ut.graphqldgs;

import com.netflix.graphql.dgs.DgsQueryExecutor;
import com.netflix.graphql.dgs.autoconfig.DgsAutoConfiguration;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = {DgsAutoConfiguration.class, PingDataFetcher.class, BigDecimalScalar.class,
        LongScalar.class, InstantScalar.class})
class PingDataFetcherTest {

    @Autowired
    DgsQueryExecutor dgsQueryExecutor;

    @Test
    void ping() {
        var query = """
                query {
                                  ping {
                                    long
                                  }
                                }""";

        var result = dgsQueryExecutor.execute(query);

        assertNotNull(result);
    }
}