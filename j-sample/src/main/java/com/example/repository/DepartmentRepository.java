package com.example.repository;

import com.example.domain.Department;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

public interface DepartmentRepository extends CrudRepository<Department, Integer>,
        JpaSpecificationExecutor<Department> {

}
