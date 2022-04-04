package com.file.graphqldgs;

import com.netflix.graphql.dgs.DgsQueryExecutor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;

import java.util.Map;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
class FileUploadMutationTest {
    
    @Autowired
    private DgsQueryExecutor dgsQueryExecutor;
    
    @Test
    void upload() {
        var query = "mutation upload($file:Upload!){ upload(file:$file)	}";
        var result = dgsQueryExecutor.executeAndExtractJsonPathAsObject(
                query,
                "data.upload",
                Map.of("file", new MockMultipartFile("test", "test.txt", "text/plain", "test content".getBytes())),
                Boolean.class
        );
        
        assertThat(result).isTrue();
    }
}
