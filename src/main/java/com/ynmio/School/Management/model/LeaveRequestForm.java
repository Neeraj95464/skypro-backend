package com.ynmio.School.Management.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class LeaveRequestForm {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String leaveType;
    private String empEmail;
    private String startDate;
    private String endDate;
    private String reason;
    private String status;

}

