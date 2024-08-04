package com.ynmio.School.Management.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

import java.time.LocalDate;

    @Entity
    @Data
    public class Payroll {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        private String employeeEmail;
        private int year;
        private int month;
        private double amount;
        private String status;
        private LocalDate createdDate;
 }
