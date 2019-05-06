package com.jineric.cassandrademo.repository;

import com.jineric.cassandrademo.model.Employee;
import org.springframework.data.repository.CrudRepository;

public interface EmployeeRepository extends CrudRepository<Employee, String> {
}
