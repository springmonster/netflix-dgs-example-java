package com.profile.datafetcher;

import com.domain.graphqldgs.types.Customer;
import com.domain.graphqldgs.types.Profile;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsData;
import com.netflix.graphql.dgs.DgsDataFetchingEnvironment;
import com.netflix.graphql.dgs.DgsEntityFetcher;

import java.util.HashMap;
import java.util.Map;

@DgsComponent
public class ProfileDataFetcher {
    Map<String, Profile> profiles = new HashMap<>();

    public ProfileDataFetcher() {
        profiles.put("1", new Profile("xxx@xxx.com", "xxxxxxxx"));
        profiles.put("2", new Profile("yyy@yyy.com", "yyyyyyyy"));
    }

    @DgsEntityFetcher(name = "Customer")
    public Customer customer(Map<String, Object> values) {
        return new Customer((String) values.get("id"), null);
    }

    @DgsData(parentType = "Customer", field = "profile")
    public Profile profileFetcher(DgsDataFetchingEnvironment dataFetchingEnvironment) {
        Customer customer = dataFetchingEnvironment.getSource();
        return profiles.get(customer.getId());
    }
}
