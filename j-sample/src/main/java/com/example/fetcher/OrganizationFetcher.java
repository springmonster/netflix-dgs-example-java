package com.example.fetcher;

import com.example.domain.Organization;
import com.example.repository.OrganizationRepository;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsQuery;
import com.netflix.graphql.dgs.InputArgument;
import com.netflix.graphql.dgs.exceptions.DgsEntityNotFoundException;

@DgsComponent
public class OrganizationFetcher {

    private OrganizationRepository repository;

    OrganizationFetcher(OrganizationRepository repository) {
        this.repository = repository;
    }

    @DgsQuery(field = "organizations")
    public Iterable<Organization> findAll() {
        return repository.findAll();
    }

    @DgsQuery(field = "organization")
    public Organization findById(@InputArgument("id") Integer id) {
        return repository.findById(id).orElseThrow(DgsEntityNotFoundException::new);
    }
}
