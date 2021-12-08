package com.netcracker.ageev.library.model.users;

import com.netcracker.ageev.library.model.BaseEntity;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class Employee  extends BaseEntity {

//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, targetEntity = Users.class)
    private Users users;

//    @ManyToOne(fetch = FetchType.LAZY,targetEntity = Role.class)
//    @JoinColumn(name = "roleId")
//    private Role roleId;
}
