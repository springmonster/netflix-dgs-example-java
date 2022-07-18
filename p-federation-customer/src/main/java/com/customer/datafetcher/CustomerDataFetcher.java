package com.customer.datafetcher;

import com.domain.graphqldgs.types.Customer;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsQuery;
import com.netflix.graphql.dgs.InputArgument;

import java.util.Random;

@DgsComponent
public class CustomerDataFetcher {

    @DgsQuery
    public Customer customer(@InputArgument String customerId) {
        return new Customer(customerId, new Random().nextInt(1, 100));
    }
}
