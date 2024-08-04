package com.ynmio.School.Management.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class Employee {

    // no use of this mode i have to delete but right now i have some other works;


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String email;
    private String hrEmail;
    private String reportingBranch;
    private String highestDegree;
    private String university;
    private int yearOfPassing;
    private String skills;
    private String certifications;
    private String familyDetails;

    // File paths
    private String photoPath;
    private String resumePath;
    private String portfolioPath;
}
