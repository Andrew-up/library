package com.netcracker.ageev.library.entity.users;

import lombok.Data;
import lombok.Getter;

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
