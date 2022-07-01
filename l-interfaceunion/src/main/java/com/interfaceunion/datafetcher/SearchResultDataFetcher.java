package com.interfaceunion.datafetcher;

import com.codegen.graphqldgs.types.Actor;
import com.codegen.graphqldgs.types.SearchResult;
import com.codegen.graphqldgs.types.Series;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsData;
import graphql.com.google.common.collect.Lists;

import java.util.List;

@DgsComponent
public class SearchResultDataFetcher {

    @DgsData(parentType = "Query", field = "search")
    public List<SearchResult> search() {
        return Lists.newArrayList(
                new Actor("actor 1"),
                new Actor("actor 2"),
                new Series("series 1"),
                new Series("series 2")
        );
    }
}
