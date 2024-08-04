package com.ynmio.School.Management.model.EmployeeData;

import com.ynmio.School.Management.model.MyUser;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class BankingInformation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String bankName;
    private String accountNumber;
    private String routingNumber;
    private String ifscCode;
    private String panNumberBanking;
    @OneToOne
    private MyUser user;
}
