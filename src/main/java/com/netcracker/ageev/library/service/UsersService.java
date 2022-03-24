package com.netcracker.ageev.library.service;

import com.netcracker.ageev.library.dto.UserDTO;
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
import java.util.Base64;
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
        byte[] decodePassword = Base64.getDecoder().decode(userIn.getPassword());
        String decodedString = new String(decodePassword);
        Users user = new Users();
        user.setEmail(userIn.getEmail());
        user.setFirstname(userIn.getName());
        user.setLastname(userIn.getSurname());
        user.setPassword(bCryptPasswordEncoder.encode(decodedString));
        user.setStatus(Status.ACTIVE);
        user.setERole(ERole.ROLE_USER);
//        user.getRoles().add(ERole.ROLE_USER);

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

    public Users updateUser(UserDTO userDTO, Principal principal) {
        Users user = getUserByPrincipal(principal);
        if(userDTO.getFirstname()!=null && !userDTO.getFirstname().equals("")){
            user.setFirstname(userDTO.getFirstname());
        }
        if(userDTO.getLastname()!=null){
            user.setLastname(userDTO.getLastname());
        }
        if(userDTO.getAddress()!=null){
            user.setAddress(userDTO.getAddress());
        }
        if (userDTO.getInfo()!=null) {
            user.setInfo(userDTO.getInfo());
        }
        if (userDTO.getPhone()!=null) {
            user.setPhone(userDTO.getPhone());
        }
        if (userDTO.getDateOfBirth()!=null) {
            user.setDateOfBirth(userDTO.getDateOfBirth());
        }
        if (userDTO.getInfo()!=null) {
            user.setInfo(userDTO.getInfo());
        }
        return userRepository.save(user);
    }

    public boolean DataAccessToUser(Users users) {
        if (users.getERole().equals(ERole.ROLE_WORKER) || (users.getERole().equals(ERole.ROLE_ADMIN))) {
            return true;
        } else {
            return false;
        }
    }

}
