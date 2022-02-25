package com.netcracker.ageev.library.service;

import com.netcracker.ageev.library.model.enums.ERole;
import com.netcracker.ageev.library.model.users.Employee;
import com.netcracker.ageev.library.model.users.Users;
import com.netcracker.ageev.library.repository.users.EmployeeRepository;
import com.netcracker.ageev.library.repository.users.UsersRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.Principal;

@Service
public class PromoCodeService {

    public static final Logger LOG = LoggerFactory.getLogger(PromoCodeService.class);

    private UsersService usersService;
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private UsersRepository usersRepository;
    private EmployeeRepository employeeRepository;

    @Autowired
    public PromoCodeService(UsersService usersService, BCryptPasswordEncoder bCryptPasswordEncoder, UsersRepository usersRepository, EmployeeRepository employeeRepository) {
        this.usersService = usersService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.usersRepository = usersRepository;
        this.employeeRepository = employeeRepository;
    }

    public String whatItPromoCode(String promoCodeName, Principal principal) {
        String response = " ";
        Users users = new Users();
        users = getUserByPrincipal(principal);
        Employee employee = new Employee();
        try {
            employee = employeeRepository.findEmployeeByUsersId(users.getId()).orElseThrow(() -> new NullPointerException("Пользователь не явряется работником"));
        } catch (NullPointerException e) {
            e.printStackTrace();
        }

        if (promoCodeName.equals("IAMAUSER")) {
            System.out.println("employee1:" + employee);
            users.setERole(ERole.ROLE_USER);
            usersRepository.save(users);
            employeeRepository.delete(employee);
            return " Промокод IAMAUSER применен";
        }
        if (promoCodeName.equals("IAMAWORKER")) {
            if (employee.getId() == null) {
                Employee employee1 = new Employee();
                employee1.setUsers(users);
                employeeRepository.save(employee1);
            }
            System.out.println("employee2:" + employee);
            users.setERole(ERole.ROLE_WORKER);
            usersRepository.save(users);
            return " Промокод IAMAWORKER применен";
        }
        if (promoCodeName.equals("IAMAADMIN")) {
            if (employee.getId() == null) {
                Employee employee1 = new Employee();
                employee1.setUsers(users);
                employeeRepository.save(employee1);
            }
            System.out.println("employee3:" + employee);
            users.setERole(ERole.ROLE_ADMIN);
            usersRepository.save(users);
            return " Промокод IAMAADMIN применен";
        }
        return "Промокод не найден";
    }

    public Users getUserByPrincipal(Principal principal) {
        String email = principal.getName();
        return usersRepository.findUsersByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username" + email));
    }

}
