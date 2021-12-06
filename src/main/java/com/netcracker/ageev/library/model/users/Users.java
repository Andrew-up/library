package com.netcracker.ageev.library.model.users;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, columnDefinition = "varchar(50)")
    private String name;
    @Column(nullable = false, columnDefinition = "varchar(50)")
    private String surname;
    @Column(nullable = true, columnDefinition = "date")
    private Date dateOfBirth;
    @Column(nullable = true)
    private String phone;
    @Column(nullable = true)
    private String address;
    @Column(nullable = false, columnDefinition = "varchar(100)")
    private String email;
    @Column(nullable = true,length = 3000)
    private String password;

    @Column(columnDefinition="boolean default false")
    private Boolean archive;

    @ManyToOne(targetEntity = UserRoles.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "usersRoleId")
    private UserRoles usersRoleId;


}
