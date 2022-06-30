package com.auth.graphqldgs;

import com.auth.graphqldgs.types.Salary;
import com.auth.graphqldgs.types.SalaryInput;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsMutation;
import com.netflix.graphql.dgs.DgsQuery;
import com.netflix.graphql.dgs.InputArgument;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
public class SalaryRestController {

    @GetMapping("/salary")
//    @PreAuthorize("hasRole('ADMIN')")
    @Secured("ROLE_ADMIN")
    public String salary() {
        return "Salary Test";
    }

    @DgsMutation
    @PreAuthorize("hasRole('HR')")
    public Salary updateSalary(@InputArgument SalaryInput salaryInput) {
        return new Salary(UUID.randomUUID().toString(), salaryInput.getEmployeeId(), salaryInput.getNewSalary());
    }
}
