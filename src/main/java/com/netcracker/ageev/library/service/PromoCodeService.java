package com.netcracker.ageev.library.service;

import com.netcracker.ageev.library.model.enums.ERole;
import com.netcracker.ageev.library.model.users.Users;
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

    @Autowired
    public PromoCodeService(UsersService usersService, BCryptPasswordEncoder bCryptPasswordEncoder, UsersRepository usersRepository) {
        this.usersService = usersService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.usersRepository = usersRepository;
    }

    public String whatItPromoCode(String promoCodeName,Principal principal){
        String response = " ";
        Users users = new Users();
        users = getUserByPrincipal(principal);
        if(promoCodeName.equals("IAMAUSER")){
            users.setERole(ERole.ROLE_USER);
            usersRepository.save(users);
            return " Промокод IAMAUSER применен";
        }
        if(promoCodeName.equals("IAMAWORKER")){
            users.setERole(ERole.ROLE_WORKER);
            usersRepository.save(users);
            return " Промокод IAMAWORKER применен";
        }
        if(promoCodeName.equals("IAMAADMIN")){
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
