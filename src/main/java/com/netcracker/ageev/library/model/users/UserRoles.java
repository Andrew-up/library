package com.netcracker.ageev.library.model.users;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class UserRoles {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, columnDefinition = "varchar(50)")
    private String jobTitle;

    @Column(nullable = false)
    private Integer roleRights;
}
