package com.netcracker.ageev.library.model.users;

import com.netcracker.ageev.library.model.BaseEntity;
import com.netcracker.ageev.library.model.enums.ERole;
import com.netcracker.ageev.library.model.enums.Status;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.*;

@Data
@Entity
public class Users extends BaseEntity implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, columnDefinition = "varchar(50)")
    private String firstname;
    @Column(nullable = false, columnDefinition = "varchar(50)")
    private String lastname;
    @Column(nullable = true, columnDefinition = "date")
    private Date dateOfBirth;
    @Column(nullable = true)
    private String phone;
    @Column(nullable = true)
    private String address;
    @Column(unique = true)
    private String email;
    @Column(nullable = true,length = 3000)
    private String password;


    @ElementCollection(targetClass = ERole.class)
    @CollectionTable(name = "user_role",joinColumns = @JoinColumn(name = "user_id"))
    @Enumerated(EnumType.STRING)
    private Set<ERole> roles = new HashSet<>();

    @Enumerated(EnumType.STRING)
    private Status status;

    @Transient
    private Collection<? extends GrantedAuthority> authorities;


    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }


    public Users(Long id, String firstname, String lastname, String email, String password, Collection<? extends GrantedAuthority> authorities,Status status) {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.password = password;
        this.email = email;
        this.authorities = authorities;
        this.status = status;
    }

    public Users(){

    }
}
