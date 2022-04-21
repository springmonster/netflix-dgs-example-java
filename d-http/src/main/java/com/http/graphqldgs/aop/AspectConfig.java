package com.http.graphqldgs.aop;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.ThreadContext;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Aspect
@Component
@Slf4j
@RequiredArgsConstructor
public class AspectConfig {

    @Pointcut("within(com.http.graphqldgs.datafetchers..*)")
    public void cut() {
    }

    @Pointcut("within(com.http.graphqldgs.service..*)")
    public void serviceCut() {
    }

    @Before("cut()")
    public void before() {
        log.info("before-----");
        ThreadContext.put("time", Instant.now().toString());
    }

    @After("cut()")
    public void after() {
        log.info("after----- {}", ThreadContext.get("time"));
    }

    @Before("serviceCut()")
    public void serviceBefore() {
        log.info("serviceBefore-----");
    }

}
