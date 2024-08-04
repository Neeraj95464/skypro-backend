package com.ynmio.School.Management.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class Assignment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "ticket_id")
    private Ticket ticket;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private MyUser user;

    private LocalDateTime assignedAt = LocalDateTime.now();

    // Getters and Setters
}

