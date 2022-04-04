package com.file.graphqldgs;

import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsMutation;
import com.netflix.graphql.dgs.InputArgument;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@DgsComponent
public class ArtworkUploadDataFetcher {
    @DgsMutation
    public Boolean addArtwork(@InputArgument MultipartFile file) throws IOException {
        Path uploadDir = Paths.get("uploaded-images");
        if (!Files.exists(uploadDir)) {
            Files.createDirectories(uploadDir);
        }
        
        Path newFile = uploadDir.resolve("show-" + UUID.randomUUID() + file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".")));
        try (OutputStream outputStream = Files.newOutputStream(newFile)) {
            outputStream.write(file.getBytes());
        }
        
        return true;
        
        // return Files.list(uploadDir)
        //         .filter(f -> f.getFileName().toString().startsWith("show-"))
        //         .map(f -> f.getFileName().toString())
        //         .map(fileName -> Image.newBuilder().url(fileName).build()).collect(Collectors.toList());
    }
}