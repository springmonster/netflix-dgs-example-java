package com.http.graphqldgs.context;

import com.netflix.graphql.dgs.context.DgsCustomContextBuilderWithRequest;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.Nullable;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.WebRequest;

import java.util.Map;

@Component
@Slf4j
public class MyContextBuilder implements DgsCustomContextBuilderWithRequest<MyContext> {

    @Override
    public MyContext build(@Nullable Map<String, ?> map, @Nullable HttpHeaders httpHeaders, @Nullable WebRequest webRequest) {
        httpHeaders.entrySet().forEach(stringListEntry -> {
            log.info("key is {}", stringListEntry.getKey());
//            stringListEntry.getValue().forEach(s -> log.info("value is {}", s));
        });
        return new MyContext();
    }
}