package com.bff.graphqldgs.datafetchers;

import com.bff.graphqldgs.BffApplication;
import com.bff.graphqldgs.client.AddShowGraphQLQuery;
import com.bff.graphqldgs.client.AddShowProjectionRoot;
import com.bff.graphqldgs.client.ShowsGraphQLQuery;
import com.bff.graphqldgs.client.ShowsProjectionRoot;
import com.bff.graphqldgs.types.Show;
import com.bff.graphqldgs.types.ShowInput;
import com.jayway.jsonpath.TypeRef;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsMutation;
import com.netflix.graphql.dgs.DgsQuery;
import com.netflix.graphql.dgs.InputArgument;
import com.netflix.graphql.dgs.client.DefaultGraphQLClient;
import com.netflix.graphql.dgs.client.GraphQLClient;
import com.netflix.graphql.dgs.client.GraphQLResponse;
import com.netflix.graphql.dgs.client.codegen.GraphQLQueryRequest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@DgsComponent
public class ShowsDataFetcher {

    @DgsQuery
    public List<Show> shows() {
        GraphQLQueryRequest graphQLQueryRequest =
                new GraphQLQueryRequest(
                        new ShowsGraphQLQuery(),
                        new ShowsProjectionRoot()
                                .id()
                                .title()
                                .releaseYear()
                );

        String query = graphQLQueryRequest.serialize();

        GraphQLClient client = new DefaultGraphQLClient("http://127.0.0.1:20001/graphql");
        GraphQLResponse response = client.executeQuery(query, new HashMap<>(), BffApplication::execute);

        var data = response.extractValueAsObject("shows", new TypeRef<List<Show>>() {
        });

        return data;
    }

    @DgsMutation
    public Show addShow(@InputArgument ShowInput input) {
        GraphQLQueryRequest graphQLQueryRequest =
                new GraphQLQueryRequest(
                        new AddShowGraphQLQuery.Builder().input(input).build(),
                        new AddShowProjectionRoot()
                                .id()
                                .title()
                                .releaseYear()
                );

        String query = graphQLQueryRequest.serialize();

//        var variables = Map.of(
//                "input", Map.of(
//                        "title", input.getTitle(),
//                        "releaseYear", input.getReleaseYear()
//                )
//        );

        GraphQLClient client = new DefaultGraphQLClient("http://127.0.0.1:20001/graphql");
        GraphQLResponse response = client.executeQuery(query, new HashMap<>(), BffApplication::execute);

        var data = response.extractValueAsObject("addShow", new TypeRef<Show>() {
        });
        return data;
    }
}


