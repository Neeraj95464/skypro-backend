package com.ynmio.School.Management.model.EmployeeData;

import com.ynmio.School.Management.model.MyUser;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class PersonalInformation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String dob;
    private String gender;
    private String address;
    private String phone;
    private String email;
    private String maritalStatus;
    @OneToOne
    private MyUser user;
}
