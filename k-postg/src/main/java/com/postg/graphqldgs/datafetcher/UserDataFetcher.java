package com.postg.graphqldgs.datafetcher;

import com.jayway.jsonpath.TypeRef;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsQuery;
import com.netflix.graphql.dgs.client.MonoGraphQLClient;
import com.netflix.graphql.dgs.client.WebClientGraphQLClient;
import com.scala.graphqldgs.types.UsersConnection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.stream.Collectors;

@DgsComponent
public class UserDataFetcher {

    @DgsQuery
    public UsersConnection allUsers() {
        String query = "query MyQuery {\n" +
                "  allUsers {\n" +
                "    nodes {\n" +
                "      userName\n" +
                "      userId\n" +
                "      id\n" +
                "      userPostsByUserId {\n" +
                "        nodes {\n" +
                "          id\n" +
                "          postId\n" +
                "        }\n" +
                "      }\n" +
                "    }\n" +
                "  }\n" +
                "}";
        WebClient webClient = WebClient.create("http://localhost:3000/graphql");
        WebClientGraphQLClient client = MonoGraphQLClient.createWithWebClient(webClient);

        return client.reactiveExecuteQuery(query).block().extractValueAsObject("allUsers", UsersConnection.class);
    }

}
