package com.start.graphqldgs.datafetchers;

import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsData;
import graphql.language.Field;
import graphql.schema.DataFetchingEnvironment;
import lombok.extern.slf4j.Slf4j;

@DgsComponent
@Slf4j
public class ListDataFetcher {

    @DgsData.List({
            @DgsData(parentType = "Query", field = "movies"),
            @DgsData(parentType = "Query", field = "games")
    })
    public String testList(DataFetchingEnvironment dfe) {
        Field field = dfe.getField();
        log.info("field name is {}", field.getName());
        return "";
    }
}
