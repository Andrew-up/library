package com.netcracker.ageev.library.service;

import com.netcracker.ageev.library.model.users.Users;
import com.netcracker.ageev.library.repository.users.UsersRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

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
        Users user = userRepository.findUsersByEmail(username).orElseThrow(() -> new  UsernameNotFoundException("юзер не найден"+ username));
        return initUser(user);
    }

    public static Users initUser(Users user) {
        List<GrantedAuthority> authorities = user.getRoles().stream()
                .map(eRole -> new SimpleGrantedAuthority(eRole.name()))
                .collect(Collectors.toList());
        System.out.println("user: "+user);
        return new Users(
                user.getId(),
                user.getFirstname(),
                user.getLastname(),
                user.getEmail(),
                user.getPassword(),
                authorities);
    }

    public Users loadUserById(Long id){
        return userRepository.findUsersById(id).orElse(null);
    }
}
