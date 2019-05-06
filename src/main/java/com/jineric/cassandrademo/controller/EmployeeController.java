package com.jineric.cassandrademo.controller;

import com.jineric.cassandrademo.model.Employee;
import com.jineric.cassandrademo.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@RestController
public class EmployeeController {

    @Autowired
    EmployeeRepository employeeRepository;

    @GetMapping(value = "/healthcheck", produces = "application/json; charset=utf-8")
    public String getHealthCheck()
    {
        return "{ \"isWorking\" : true }";
    }

    @GetMapping("/employees")
    public List<Employee> getEmployees()
    {
        Iterable<Employee> result = employeeRepository.findAll();
        List<Employee> employeesList = new ArrayList<Employee>();
        result.forEach(employeesList::add);
        return employeesList;
    }

    @GetMapping("/employees/{id}")
    public Optional<Employee> getEmployee(@PathVariable String id)
    {
        Optional<Employee> emp = employeeRepository.findById(id);
        return emp;
    }

    @PutMapping("/employees/{id}")
    public Optional<Employee> updateEmployee(@RequestBody Employee newEmployee, @PathVariable String id)
    {
        Optional<Employee> optionalEmp = employeeRepository.findById(id);
        if (optionalEmp.isPresent()) {
            Employee emp = optionalEmp.get();
            emp.setFirstName(newEmployee.getFirstName());
            emp.setLastName(newEmployee.getLastName());
            emp.setEmail(newEmployee.getEmail());
            employeeRepository.save(emp);
        }
        return optionalEmp;
    }

    @DeleteMapping(value = "/employees/{id}", produces = "application/json; charset=utf-8")
    public String deleteEmployee(@PathVariable String id) {
        Boolean result = employeeRepository.existsById(id);
        employeeRepository.deleteById(id);
        return "{ \"success\" : "+ (result ? "true" : "false") +" }";
    }

    @PostMapping("/employees")
    public Employee addEmployee(@RequestBody Employee newEmployee)
    {
        String id = String.valueOf(new Random().nextInt());
        Employee emp = new Employee(id, newEmployee.getFirstName(), newEmployee.getLastName(), newEmployee.getEmail());
        employeeRepository.save(emp);
        return emp;
    }
}
