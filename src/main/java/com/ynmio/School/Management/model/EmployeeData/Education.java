package com.ynmio.School.Management.model.EmployeeData;

import com.ynmio.School.Management.model.MyUser;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Education {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String highestQualification;
    private String certifications;
    private String institutionsAttended;
    @OneToOne
    private MyUser user;
}
