package com.project.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
public class Application {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int id;

    @Column(nullable=false)
    private LocalDate appliedAt;

    @ManyToOne
    private Seeker seeker;

    @ManyToOne
    private Job job;
}
