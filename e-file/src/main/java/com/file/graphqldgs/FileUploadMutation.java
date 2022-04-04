package com.file.graphqldgs;

import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsMutation;
import com.netflix.graphql.dgs.InputArgument;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StreamUtils;
import org.springframework.web.multipart.MultipartFile;

import java.nio.charset.StandardCharsets;

@DgsComponent
@RequiredArgsConstructor
@Slf4j
public class FileUploadMutation {
    
    @DgsMutation
    public Boolean upload(@InputArgument("file") MultipartFile upload) {
        printFileInfo(upload);
        return true;
    }
    
    @SneakyThrows
    private void printFileInfo(MultipartFile file) {
        log.info("file name: {}", file.getName());
        log.info("file original file name: {}", file.getOriginalFilename());
        log.info("file content type: {}", file.getContentType());
        String fileContent = StreamUtils.copyToString(file.getInputStream(), StandardCharsets.UTF_8);
        log.info("file content: {}", fileContent);
    }
}
