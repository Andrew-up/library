package com.netcracker.ageev.library.model.users;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.netcracker.ageev.library.model.BaseEntity;
import com.netcracker.ageev.library.model.enums.ERole;
import com.netcracker.ageev.library.model.enums.Status;
import lombok.Data;
import net.minidev.json.annotate.JsonIgnore;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
public class Users implements UserDetails {
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
    @Column(unique = true, nullable = false)
    private String email;
    @Column(nullable = false, length = 3000)
    @JsonIgnore
    private String password;

    @Enumerated(EnumType.STRING)
    private Status status;

    @ElementCollection(targetClass = ERole.class)
    @CollectionTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"))
    @Enumerated(EnumType.STRING)
    private Set<ERole> roles = new HashSet<>();

    @Transient
    private Collection<? extends GrantedAuthority> authorities;

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    @JsonProperty(value = "password")
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


    public Users(Long id, String firstname, String lastname, String email, String password, Collection<? extends GrantedAuthority> authorities, Status status) {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.password = password;
        this.email = email;
        this.authorities = authorities;
        this.status = status;
    }

    public Users() {

    }
}
