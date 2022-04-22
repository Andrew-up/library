package com.netcracker.ageev.library.service;

import com.netcracker.ageev.library.controller.AuthController;
import com.netcracker.ageev.library.dto.UserDTO;
import com.netcracker.ageev.library.model.enums.ERole;
import com.netcracker.ageev.library.model.enums.Status;
import com.netcracker.ageev.library.model.users.Employee;
import com.netcracker.ageev.library.model.users.Users;
import com.netcracker.ageev.library.payload.request.SignupRequest;
import com.netcracker.ageev.library.payload.responce.RegistrationException;
import com.netcracker.ageev.library.repository.users.EmployeeRepository;
import com.netcracker.ageev.library.repository.users.UsersRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.Base64;
import java.util.List;


@Service
public class UsersService {

    private static final Logger LOG = LoggerFactory.getLogger(UsersService.class);
    private final UsersRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final EmployeeRepository employeeRepository;

    @Autowired
    public UsersService(UsersRepository userRepository,
                        BCryptPasswordEncoder bCryptPasswordEncoder,
                        EmployeeRepository employeeRepository) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.userRepository = userRepository;
        this.employeeRepository = employeeRepository;
    }

    public Users createUser(SignupRequest userIn) {
        byte[] decodePassword = Base64.getDecoder().decode(userIn.getPassword());
        String decodedString = new String(decodePassword);
        Users user = new Users();
        user.setEmail(userIn.getEmail());
        user.setFirstname(userIn.getName());
        user.setLastname(userIn.getSurname());
        user.setPassword(bCryptPasswordEncoder.encode(decodedString));
        user.setStatus(Status.ACTIVE);
        user.setERole(ERole.ROLE_USER);
        try {
            return userRepository.save(user);
        } catch (RuntimeException e) {
            LOG.error(e.getMessage());
            throw new RegistrationException();
        }
    }


    public Users getCurrentUser(Principal principal) {
        return getUserByPrincipal(principal);
    }

    public List<Users> getAllUsers() {
        return userRepository.findAllByOrderById();
    }

    public List<Users> getAllUsersRequestCreated() {
        return userRepository.findAllByBasketByUserCustomQuery();
    }

    public Users getUserById(Long userId) {
        return userRepository.findUsersById(userId).orElseThrow(() -> new UsernameNotFoundException("user not found"));
    }

    public Users findUsersByEmail(String email) {
        return userRepository.findUsersByEmail(email).orElseThrow(() -> new UsernameNotFoundException("user not found"));
    }

    public Users getUserByPrincipal(Principal principal) {
        String email = principal.getName();
        return userRepository.findUsersByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username" + email));
    }

    public Users updateUser(UserDTO userDTO, Principal principal) {
        Users user = getUserByPrincipal(principal);
        if (userDTO.getFirstname() != null && !userDTO.getFirstname().equals("")) {
            user.setFirstname(userDTO.getFirstname());
        }
        if (userDTO.getLastname() != null) {
            user.setLastname(userDTO.getLastname());
        }
        if (userDTO.getAddress() != null) {
            user.setAddress(userDTO.getAddress());
        }
        if (userDTO.getInfo() != null) {
            user.setInfo(userDTO.getInfo());
        }
        if (userDTO.getPhone() != null) {
            user.setPhone(userDTO.getPhone());
        }
        if (userDTO.getDateOfBirth() != null) {
            user.setDateOfBirth(userDTO.getDateOfBirth());
        }
        if (userDTO.getInfo() != null) {
            user.setInfo(userDTO.getInfo());
        }
        return userRepository.save(user);
    }

    public Users updateUserRoleOrActive(UserDTO userDTO) {
        Users users = userRepository.findUsersById(userDTO.getId()).orElseThrow(() -> new UsernameNotFoundException("not found"));
        Employee employee;
        if (userDTO.getNewRole() != null) {
            if (userDTO.getNewRole().equals("ROLE_ADMIN")) {
                users.setERole(ERole.ROLE_ADMIN);
                employee = new Employee();
                employee.setUsers(users);
                employeeRepository.save(employee);
            }
            if (userDTO.getNewRole().equals("ROLE_USER")) {
                users.setERole(ERole.ROLE_USER);
            }
            if (userDTO.getNewRole().equals("ROLE_WORKER")) {
                users.setERole(ERole.ROLE_WORKER);
                employee = new Employee();
                employee.setUsers(users);
                employeeRepository.save(employee);
            }
        }


        if (userDTO.getNewStatus() != null) {
            if (userDTO.getNewStatus().equals("ACTIVE")) {
                users.setStatus(Status.ACTIVE);
            }
            if (userDTO.getNewStatus().equals("NOT_ACTIVE")) {
                users.setStatus(Status.NOT_ACTIVE);
            }
        }
        return userRepository.save(users);

    }

}
