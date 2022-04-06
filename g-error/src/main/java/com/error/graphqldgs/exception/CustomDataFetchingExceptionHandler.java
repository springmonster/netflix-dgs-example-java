package com.error.graphqldgs.exception;

import com.netflix.graphql.dgs.exceptions.DefaultDataFetcherExceptionHandler;
import com.netflix.graphql.types.errors.TypedGraphQLError;
import graphql.ErrorClassification;
import graphql.ErrorType;
import graphql.GraphQLError;
import graphql.GraphqlErrorBuilder;
import graphql.execution.DataFetcherExceptionHandler;
import graphql.execution.DataFetcherExceptionHandlerParameters;
import graphql.execution.DataFetcherExceptionHandlerResult;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class CustomDataFetchingExceptionHandler implements DataFetcherExceptionHandler {
    private final DefaultDataFetcherExceptionHandler defaultHandler = new DefaultDataFetcherExceptionHandler();

    @Override
    public DataFetcherExceptionHandlerResult onException(DataFetcherExceptionHandlerParameters handlerParameters) {
        Throwable exception = handlerParameters.getException();
        if (exception instanceof ShowNotFoundException) {
            Map<String, Object> debugInfo = new HashMap<>();
            debugInfo.put("somefield", "somevalue");

            GraphQLError graphqlError = TypedGraphQLError.newNotFoundBuilder()
                    .message(exception.getMessage())
                    .debugInfo(debugInfo)
                    .path(handlerParameters.getPath())
                    .build();
            return DataFetcherExceptionHandlerResult.newResult()
                    .error(graphqlError)
                    .build();
        } else if (exception instanceof RatingNotFoundException) {
            // 这里还有Error.Type
            GraphQLError graphqlError = GraphqlErrorBuilder.newError()
                    .errorType(MyErrorType.RATING_NOT_FOUND)
                    .message(exception.getMessage())
                    .path(handlerParameters.getPath())
                    .build();

            return DataFetcherExceptionHandlerResult.newResult()
                    .error(graphqlError)
                    .build();
        } else {
            return defaultHandler.onException(handlerParameters);
        }
    }
}

enum MyErrorType implements ErrorClassification {
    RATING_NOT_FOUND
}