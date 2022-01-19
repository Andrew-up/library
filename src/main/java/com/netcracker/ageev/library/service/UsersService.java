package com.netcracker.ageev.library.service;

import com.netcracker.ageev.library.model.enums.ERole;
import com.netcracker.ageev.library.model.enums.Status;
import com.netcracker.ageev.library.model.users.Users;
import com.netcracker.ageev.library.payload.request.SignupRequest;
import com.netcracker.ageev.library.repository.users.UsersRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;

@Service
public class UsersService {

    public static final Logger LOG = LoggerFactory.getLogger(UsersService.class);
    private final UsersRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UsersService(UsersRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.userRepository = userRepository;

    }

    public Users createUser(SignupRequest userIn) {
        Users user = new Users();
        user.setEmail(userIn.getEmail());
        user.setFirstname(userIn.getName());
        user.setLastname(userIn.getSurname());
        user.setPassword(bCryptPasswordEncoder.encode(userIn.getPassword()));
        user.setStatus(Status.ACTIVE);
        user.setERole(ERole.ROLE_USER);
        user.getRoles().add(ERole.ROLE_USER);

        try {
            LOG.info("Saving user {} ", userIn.getEmail());
            return userRepository.save(user);
        } catch (Exception e) {
            LOG.error("Error registration {} ", e.getMessage());

            // TODO: добавить свое исключение сюда
            throw new UsernameNotFoundException("The user " + user.getEmail() + "already exist!");

        }
    }


    public Users getCurrentUser(Principal principal){
        return getUserByPrincipal(principal);
    }

    public List<Users> getAllUsers(){
        return userRepository.findAllByOrderById();
    }

    public Users getUserById(Long userId) {
        return userRepository.findUsersById(userId).orElseThrow(() -> new UsernameNotFoundException("user not found"));
    }

    public Users findUsersByEmail(String email){
        return userRepository.findUsersByEmail(email).orElseThrow(() ->  new UsernameNotFoundException("user not found"));
    }

    public Users getUserByPrincipal(Principal principal) {
        String email = principal.getName();
        return userRepository.findUsersByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username" + email));
    }

}
