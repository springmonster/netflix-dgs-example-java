package com.auth.graphqldgs;

import com.auth.graphqldgs.types.Salary;
import com.auth.graphqldgs.types.SalaryInput;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsMutation;
import com.netflix.graphql.dgs.DgsQuery;
import com.netflix.graphql.dgs.InputArgument;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.UUID;

@DgsComponent
public class SalaryController {

    @DgsQuery
    @PreAuthorize("hasRole('ADMIN')")
    public String salary() {
        return "Salary Test";
    }

    @DgsMutation
    @PreAuthorize("hasRole('HR')")
    public Salary updateSalary(@InputArgument SalaryInput salaryInput) {
        return new Salary(UUID.randomUUID().toString(), salaryInput.getEmployeeId(), salaryInput.getNewSalary());
    }
}
