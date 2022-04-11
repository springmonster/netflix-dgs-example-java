package com.codegen.graphqldgs.datafetchers;

import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsQuery;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestHeader;

@DgsComponent
@Slf4j
public class HelloDataFetcher {

    @DgsQuery
    public String hello(@RequestHeader String header) {
        log.info("Request header host is {}", header);
        return "hello";
    }
}
