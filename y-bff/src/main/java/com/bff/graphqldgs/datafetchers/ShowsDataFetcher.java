package com.bff.graphqldgs.datafetchers;

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
import com.netflix.graphql.dgs.client.MonoGraphQLClient;
import com.netflix.graphql.dgs.client.codegen.GraphQLQueryRequest;

import java.util.List;

@DgsComponent
public class ShowsDataFetcher {

    final MonoGraphQLClient monoGraphQLClient;

    public ShowsDataFetcher(MonoGraphQLClient monoGraphQLClient) {
        this.monoGraphQLClient = monoGraphQLClient;
    }

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

        return monoGraphQLClient.reactiveExecuteQuery(query).block().extractValueAsObject("shows", new TypeRef<List<Show>>() {
        });
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
        return monoGraphQLClient.reactiveExecuteQuery(query).block().extractValueAsObject("addShow", new TypeRef<Show>() {
        });
    }
}
