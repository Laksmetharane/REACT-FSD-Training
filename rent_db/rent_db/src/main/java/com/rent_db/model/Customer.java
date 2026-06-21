package com.rent_db.model;

import com.rent_db.enums.Role;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


@Entity
@Getter
@Setter
public class Customer {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false,unique = true)
    private String mob_no;

    @Column(unique=true)
    private String email;

    @Column(nullable = false)
    private int dl_no;

    @Enumerated(EnumType.STRING)
    Role role;

    @OneToOne
    public User user;

    @ManyToOne
    private Admin admin;

    private String docIdPath;

    private boolean isActive = true;
}
