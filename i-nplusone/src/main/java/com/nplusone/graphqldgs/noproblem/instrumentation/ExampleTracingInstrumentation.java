package com.nplusone.graphqldgs.noproblem.instrumentation;

import graphql.ExecutionResult;
import graphql.execution.instrumentation.InstrumentationContext;
import graphql.execution.instrumentation.InstrumentationState;
import graphql.execution.instrumentation.SimpleInstrumentation;
import graphql.execution.instrumentation.parameters.InstrumentationExecutionParameters;
import graphql.execution.instrumentation.parameters.InstrumentationFieldFetchParameters;
import graphql.schema.*;
import graphql.validation.schemawiring.FieldValidatorDataFetcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.concurrent.CompletableFuture;

@Component
public class ExampleTracingInstrumentation extends SimpleInstrumentation {
    private final static Logger LOGGER = LoggerFactory.getLogger(ExampleTracingInstrumentation.class);

    @Override
    public InstrumentationState createState() {
        return new TracingState();
    }

    @Override
    public InstrumentationContext<ExecutionResult> beginExecution(InstrumentationExecutionParameters parameters) {
        TracingState tracingState = parameters.getInstrumentationState();
        tracingState.startTime = System.currentTimeMillis();
        return super.beginExecution(parameters);
    }

    @Override
    public DataFetcher<?> instrumentDataFetcher(DataFetcher<?> dataFetcher, InstrumentationFieldFetchParameters parameters) {
        // We only care about user code
        if (parameters.isTrivialDataFetcher()) {
            return dataFetcher;
        }

        if (isPropertyFetcher(dataFetcher)) {
            return dataFetcher;
        }

        return environment -> {
            long startTime = System.currentTimeMillis();
            Object result = dataFetcher.get(environment);
            if (result instanceof CompletableFuture) {
                ((CompletableFuture<?>) result).whenComplete((r, ex) -> {
                    long totalTime = System.currentTimeMillis() - startTime;
                    LOGGER.info("Async datafetcher {} took {}ms", findDatafetcherTag(parameters), totalTime);
                });
            } else {
                long totalTime = System.currentTimeMillis() - startTime;
                LOGGER.info("Datafetcher {} took {}ms", findDatafetcherTag(parameters), totalTime);
            }

            return result;
        };
    }

    private boolean isPropertyFetcher(DataFetcher<?> dataFetcher) {
        if (dataFetcher instanceof FieldValidatorDataFetcher) {
            Field field = ReflectionUtils.findField(FieldValidatorDataFetcher.class, "defaultDataFetcher");
            ReflectionUtils.makeAccessible(field);
            try {
                Object o = field.get(dataFetcher);
                return o instanceof PropertyDataFetcher;
            } catch (IllegalAccessException e) {
                e.printStackTrace();
                return false;
            }
        }
        return false;
    }

    @Override
    public CompletableFuture<ExecutionResult> instrumentExecutionResult(ExecutionResult executionResult, InstrumentationExecutionParameters parameters) {
        TracingState tracingState = parameters.getInstrumentationState();
        long totalTime = System.currentTimeMillis() - tracingState.startTime;
        LOGGER.info("Total execution time: {}ms", totalTime);

        return super.instrumentExecutionResult(executionResult, parameters);
    }

    private String findDatafetcherTag(InstrumentationFieldFetchParameters parameters) {
        GraphQLOutputType type = parameters.getExecutionStepInfo().getParent().getType();
        GraphQLObjectType parent;
        if (type instanceof GraphQLNonNull) {
            parent = (GraphQLObjectType) ((GraphQLNonNull) type).getWrappedType();
        } else {
            parent = (GraphQLObjectType) type;
        }

        return parent.getName() + "." + parameters.getExecutionStepInfo().getPath().getSegmentName();
    }

    static class TracingState implements InstrumentationState {
        long startTime;
    }
}
