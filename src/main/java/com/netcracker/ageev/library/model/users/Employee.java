package com.netcracker.ageev.library.model.users;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, targetEntity = Users.class)
    private Users users;

    @ManyToOne(fetch = FetchType.LAZY,targetEntity = UserRoles.class)
    @JoinColumn(name = "roleId")
    private UserRoles roleId;
}
