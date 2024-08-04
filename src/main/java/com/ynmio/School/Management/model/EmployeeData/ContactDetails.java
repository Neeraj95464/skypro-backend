package com.ynmio.School.Management.model.EmployeeData;

import com.ynmio.School.Management.model.MyUser;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class ContactDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String emergencyContactName;
    private String emergencyContactNumber;
    private String emergencyContactEmail;
    private String fathersName;
    private String fathersDob;
    private String fathersAadhar;
    private String mothersName;
    private String mothersDob;
    private String mothersAadhar;
    private String spouseName;
    @OneToOne
    private MyUser user;
}
