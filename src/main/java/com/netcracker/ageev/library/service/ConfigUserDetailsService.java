package com.netcracker.ageev.library.service;

import com.netcracker.ageev.library.model.users.Users;
import com.netcracker.ageev.library.repository.users.UsersRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class ConfigUserDetailsService implements UserDetailsService {

    private final UsersRepository userRepository;

    public ConfigUserDetailsService(UsersRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users user = userRepository.findUsersByEmail(username).orElseThrow(() -> new UsernameNotFoundException("юзер не найден" + username));
        return initUser(user);
    }

    public static Users initUser(Users user) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        System.out.println("user: " + user);

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

    public Users loadUserById(Long id) {
        return userRepository.findUsersById(id).orElse(null);
    }
}
