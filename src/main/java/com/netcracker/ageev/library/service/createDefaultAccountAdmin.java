package com.netcracker.ageev.library.service;

import com.netcracker.ageev.library.model.enums.ERole;
import com.netcracker.ageev.library.model.enums.Status;
import com.netcracker.ageev.library.model.users.Employee;
import com.netcracker.ageev.library.model.users.Users;
import com.netcracker.ageev.library.repository.users.EmployeeRepository;
import com.netcracker.ageev.library.repository.users.UsersRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class createDefaultAccountAdmin implements CommandLineRunner {
    private static final Logger LOG = LoggerFactory.getLogger(createDefaultAccountAdmin.class);
    private final UsersRepository userRepository;
    private final EmployeeRepository employeeRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public createDefaultAccountAdmin(UsersRepository userRepository,
                                     EmployeeRepository employeeRepository,
                                     BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.employeeRepository = employeeRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {
        Optional<Users> users = userRepository.findUsersByEmail("admin@library.ru");
        if (!users.isPresent()) {
            Employee employee = new Employee();
            Users users1 = new Users();
            users1.setEmail("admin@library.ru");
            users1.setFirstname("admin");
            users1.setLastname("admin");
            users1.setERole(ERole.ROLE_ADMIN);
            users1.setStatus(Status.ACTIVE);
            users1.setPassword(bCryptPasswordEncoder.encode("admin"));
            userRepository.save(users1);
            employee.setUsers(users1);
            System.out.println("admin created");
            LOG.info("Admin account created");
            employeeRepository.save(employee);
        } else {
            LOG.info("Admin account already exists");
        }
    }
}
