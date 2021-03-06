package com.netcracker.ageev.library.service;

import com.netcracker.ageev.library.exception.DataNotFoundException;
import com.netcracker.ageev.library.model.users.Employee;
import com.netcracker.ageev.library.repository.users.EmployeeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmployeeService {

    public static final Logger LOG = LoggerFactory.getLogger(EmployeeService.class);

    private final EmployeeRepository employeeRepository;


    @Autowired
    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public Employee getEmployeeById(Long id) {
        Employee employee = new Employee();
        try {
            return employeeRepository.findEmployeeByUsersId(id).orElseThrow(() -> new DataNotFoundException("not found. getEmployeeById" + id));
        } catch (DataNotFoundException e) {
            return employee;
        }
    }
}
