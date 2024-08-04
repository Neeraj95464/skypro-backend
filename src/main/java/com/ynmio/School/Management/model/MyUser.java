package com.ynmio.School.Management.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
public class MyUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String email;
    private String password;
    private String role;

    @ManyToOne
    @JoinColumn(name = "department_id")
    private Department department;

}
