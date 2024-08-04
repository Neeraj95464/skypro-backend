package com.ynmio.School.Management.model.EmployeeData;

import com.ynmio.School.Management.model.MyUser;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.stereotype.Component;

@Entity
@Data
@Component
public class Uploads {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String photoPath;
    private String resumePath;
    private String portfolioPath;
    @OneToOne
    private MyUser user;
}
