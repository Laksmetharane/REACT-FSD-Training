package com.rent_db.model;

import com.rent_db.enums.Role;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Admin {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int id;

    @Column(nullable=false)
    private String name;

    @Column(nullable=false)
    private int age;

    @Enumerated(EnumType.STRING)
    Role role;

    @OneToOne
    private User user;

    private String idPath;
}
