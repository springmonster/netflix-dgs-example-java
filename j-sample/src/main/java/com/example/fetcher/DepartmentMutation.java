package com.example.fetcher;

import com.example.domain.Department;
import com.example.domain.DepartmentInput;
import com.example.domain.Organization;
import com.example.repository.DepartmentRepository;
import com.example.repository.OrganizationRepository;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsMutation;

@DgsComponent
public class DepartmentMutation {

    DepartmentRepository departmentRepository;
    OrganizationRepository organizationRepository;

    DepartmentMutation(DepartmentRepository departmentRepository, OrganizationRepository organizationRepository) {
        this.departmentRepository = departmentRepository;
        this.organizationRepository = organizationRepository;
    }

    @DgsMutation(field = "newDepartment")
    public Department newDepartment(DepartmentInput departmentInput) {
        Organization organization = organizationRepository.findById(departmentInput.getOrganizationId()).orElseThrow();
        return departmentRepository.save(new Department(null, departmentInput.getName(), null, organization));
    }

}
