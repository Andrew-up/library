package com.netcracker.ageev.library.service;

import com.netcracker.ageev.library.model.enums.ERole;
import com.netcracker.ageev.library.model.users.Users;
import com.netcracker.ageev.library.repository.users.UsersRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ConfigUserDetailsService implements UserDetailsService {

    private final UsersRepository userRepository;

    public ConfigUserDetailsService(UsersRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // TODO: добавить свое исключение сюда
        Users user = userRepository.findUsersByEmail(username).orElseThrow(() -> new  UsernameNotFoundException("юзер не найден"+ username));
        return initUser(user);
    }

    public static Users initUser(Users user) {
//        List<GrantedAuthority> authorities = user.getRoles().stream()
//                .map(eRole -> new SimpleGrantedAuthority(eRole.name()))
//                .collect(Collectors.toList());
//
//        List<GrantedAuthority> authoritiesList = new ArrayList<>();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
//        List<GrantedAuthority> listAuthorities = new ArrayList<GrantedAuthority>();
//        listAuthorities.addAll(authorities);
        System.out.println("user: "+user);



        return new Users(
                user.getId(),
                user.getFirstname(),
                user.getLastname(),
                user.getEmail(),
                user.getPassword(),
                authorities,
                user.getStatus(),
                user.getERole());
    }

    public Users loadUserById(Long id){
        return userRepository.findUsersById(id).orElse(null);
    }
}
