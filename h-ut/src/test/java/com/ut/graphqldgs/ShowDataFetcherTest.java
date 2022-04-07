package com.ut.graphqldgs;

import com.netflix.graphql.dgs.DgsQueryExecutor;
import com.netflix.graphql.dgs.autoconfig.DgsAutoConfiguration;
import com.netflix.graphql.dgs.client.codegen.GraphQLQueryRequest;
import com.ut.graphqldgs.client.AddShowGraphQLQuery;
import com.ut.graphqldgs.client.AddShowProjectionRoot;
import com.ut.graphqldgs.client.ShowsGraphQLQuery;
import com.ut.graphqldgs.client.ShowsProjectionRoot;
import com.ut.graphqldgs.types.Show;
import com.ut.graphqldgs.types.ShowInput;
import graphql.ExecutionResult;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = {DgsAutoConfiguration.class, ShowDataFetcher.class})
class ShowDataFetcherTest {

    @Autowired
    DgsQueryExecutor dgsQueryExecutor;

    @MockBean
    ShowsService showsService;

    @BeforeEach
    public void before() {
        Mockito.when(showsService.shows())
                .thenAnswer(invocation -> Collections.singletonList(Show.newBuilder().id(1).title("mock title").releaseYear(2020).build()));
    }

    @Test
    void greeting() {
        String greeting = dgsQueryExecutor.executeAndExtractJsonPath(
                "{ greeting }",
                "data.greeting"
        );

        assertThat(greeting).isEqualTo("greeting!");
    }

    @Test
    void shows() {
        List<String> titles = dgsQueryExecutor.executeAndExtractJsonPath(
                " { shows { title releaseYear }}",
                "data.shows[*].title");

        assertThat(titles).contains("mock title");
    }

    /**
     * 这是不使用请求字符串的形式编写单元测试
     */
    @Test
    public void showsWithQueryApi() {
        GraphQLQueryRequest graphQLQueryRequest = new GraphQLQueryRequest(
                new ShowsGraphQLQuery.Builder().titleFilter("").build(),
                new ShowsProjectionRoot().title()
        );

        List<String> titles = dgsQueryExecutor.executeAndExtractJsonPath(graphQLQueryRequest.serialize(), "data.shows[*].title");
        assertThat(titles).containsExactly("mock title");
    }

    /**
     * Exception的测试
     */
    @Test
    void showsWithException() {
        Mockito.when(showsService.shows()).thenThrow(new RuntimeException("nothing to see here"));
        ExecutionResult result = dgsQueryExecutor.execute(" { shows { title releaseYear }}");
        assertThat(result.getErrors()).isNotEmpty();
        assertThat(result.getErrors().get(0).getMessage()).isEqualTo("java.lang.RuntimeException: nothing to see here");
    }

    @Test
    void addShowMutation() {
        ShowInput showInput = ShowInput.newBuilder().title("title").releaseYear(2022).build();

        GraphQLQueryRequest graphQLQueryRequest = new GraphQLQueryRequest(
                AddShowGraphQLQuery.newRequest()
                        .input(showInput)
                        .build(),
                new AddShowProjectionRoot().releaseYear());

        Integer year = dgsQueryExecutor.executeAndExtractJsonPath(graphQLQueryRequest.serialize(), "data.addShow.releaseYear");

        assertThat(year).isEqualTo(2022);
    }

    @Test
    void addShowMutationWithExecute() {
        ShowInput showInput = ShowInput.newBuilder().title("title").releaseYear(2022).build();

        GraphQLQueryRequest graphQLQueryRequest = new GraphQLQueryRequest(
                AddShowGraphQLQuery.newRequest()
                        .input(showInput)
                        .build(),
                new AddShowProjectionRoot().releaseYear());

        ExecutionResult executionResult = dgsQueryExecutor.execute(graphQLQueryRequest.serialize());

        assertThat(executionResult.getErrors()).isEmpty();
    }
}