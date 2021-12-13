package com.netcracker.ageev.library.model;

import com.netcracker.ageev.library.model.users.Users;
import lombok.Data;
import lombok.Getter;

import javax.persistence.*;
import java.time.Instant;

@Entity
@Data
public class RefreshToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false, unique = true)
    private String token;

    @OneToOne
    @JoinColumn(name = "user_id",referencedColumnName = "id")
    private Users user;

    @Column(nullable = false)
    private Instant expiryDate;

}
