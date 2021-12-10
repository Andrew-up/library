package com.netcracker.ageev.library.model.books;

import com.netcracker.ageev.library.model.BaseEntity;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class Publisher extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String name;

}
