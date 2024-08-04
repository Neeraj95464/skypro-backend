package com.ynmio.School.Management.model.EmployeeData;

import com.ynmio.School.Management.model.MyUser;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class WorkExperience {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String workExperience;
    private String previousCompany;
    private String jobTitleExperience;
    private String duration;
    private String skills;
    @OneToOne
    private MyUser user;
}
