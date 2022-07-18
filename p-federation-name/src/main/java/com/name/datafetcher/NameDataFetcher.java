package com.name.datafetcher;

import com.domain.graphqldgs.types.Customer;
import com.domain.graphqldgs.types.Name;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsData;
import com.netflix.graphql.dgs.DgsDataFetchingEnvironment;
import com.netflix.graphql.dgs.DgsEntityFetcher;

import java.util.HashMap;
import java.util.Map;

@DgsComponent
public class NameDataFetcher {

    Map<String, Name> names = new HashMap<>();

    public NameDataFetcher() {
        names.put("1", new Name("1-1 first", "1-1 last", "1-1 middle", "1-1 full", "1-1 prefix"));

        names.put("2", new Name("2-1 first", "2-1 last", "2-1 middle", "2-1 full", "2-1 prefix"));
    }

    @DgsEntityFetcher(name = "Customer")
    public Customer customer(Map<String, Object> values) {
        return new Customer((String) values.get("id"), null);
    }

    @DgsData(parentType = "Customer", field = "name")
    public Name nameFetcher(DgsDataFetchingEnvironment dataFetchingEnvironment) {
        Customer customer = dataFetchingEnvironment.getSource();
        return names.get(customer.getId());
    }
}
