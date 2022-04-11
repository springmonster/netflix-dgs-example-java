package com.http.graphqldgs.datafetchers;

import com.http.graphqldgs.context.MyContext;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsData;
import com.netflix.graphql.dgs.InputArgument;
import com.netflix.graphql.dgs.context.DgsContext;
import graphql.schema.DataFetchingEnvironment;

@DgsComponent
public class ContextDataFetcher {

    @DgsData(parentType = "Query", field = "withContext")
    public String withContext(@InputArgument String input, DataFetchingEnvironment dfe) {
        MyContext customContext = DgsContext.getCustomContext(dfe);
        DgsContext.getRequestData(dfe).getHeaders();
        return customContext.getCustomState() + " : " + input;
    }
}
