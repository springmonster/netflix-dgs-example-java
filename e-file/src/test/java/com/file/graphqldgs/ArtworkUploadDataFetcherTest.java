package com.file.graphqldgs;

import com.netflix.graphql.dgs.DgsQueryExecutor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
class ArtworkUploadDataFetcherTest {

    @Autowired
    private DgsQueryExecutor dgsQueryExecutor;

    @Test
    void addArtwork() {
        var query = "mutation addArtwork($file:Upload!){ addArtwork(file:$file) {url} }";
        var result = dgsQueryExecutor.executeAndExtractJsonPathAsObject(
                query,
                "data.addArtwork",
                Map.of("file", new MockMultipartFile("test", "test.txt", "text/plain", "test content".getBytes())),
                List.class
        );

        System.out.println(result);
        assertThat(result.size()).isNotZero();
    }
}