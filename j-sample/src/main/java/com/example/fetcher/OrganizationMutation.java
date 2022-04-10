package com.example.fetcher;

import com.example.domain.Organization;
import com.example.domain.OrganizationInput;
import com.example.repository.OrganizationRepository;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsData;

@DgsComponent
public class OrganizationMutation {

    OrganizationRepository repository;

    OrganizationMutation(OrganizationRepository repository) {
        this.repository = repository;
    }

    @DgsData(parentType = "MutationResolver", field = "newOrganization")
    public Organization newOrganization(OrganizationInput organizationInput) {
        return repository.save(new Organization(null, organizationInput.getName(), null, null));
    }

}
