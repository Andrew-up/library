package com.netcracker.ageev.library.model.books;

import com.netcracker.ageev.library.model.BaseEntity;
import com.netcracker.ageev.library.model.users.Users;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class AgeLimit  {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false,unique = true)
    private String age;

}
