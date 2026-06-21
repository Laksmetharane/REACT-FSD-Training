package com.rent_db.model;

import com.rent_db.enums.Role;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class RentalAgent {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int id;

    @Column(nullable=false)
    private String name;

    @Enumerated(EnumType.STRING)
    Role role;

    @Column(nullable=false)
    private String mob_no;

    @Column(nullable=false)
    private String Address;

    @OneToOne
    private User user;

    private boolean isActive = true;
}
