package com.example.repository;

import com.example.domain.Employee;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

public interface EmployeeRepository extends CrudRepository<Employee, Integer>,
        JpaSpecificationExecutor<Employee> {
}
