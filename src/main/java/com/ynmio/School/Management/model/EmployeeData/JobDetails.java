package com.ynmio.School.Management.model.EmployeeData;

import com.ynmio.School.Management.model.MyUser;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class JobDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String jobTitle;
    private String department;
    private String dateOfJoining;
    private String hrName;
    private String hrEmail;
    private String reportingBranch;
    @OneToOne
    private MyUser user;
}
