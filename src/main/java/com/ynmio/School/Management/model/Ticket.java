package com.ynmio.School.Management.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
@Data
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String ticketId;
    private LocalDateTime createdAt;
    private String status;

        @Column(nullable = false)
        private String type; // Individual, Company, Dealer, Other

        @Column(nullable = false)
        private String customerName;

        @Column(nullable = false)
        private String email;

        @Column(nullable = false)
        private String address;

        @Column(nullable = false)
        private String city;

        @Column(nullable = false)
        private String pincode;

        private String companyName;
        private String gstNo;
        private String address2;
        @Column(nullable = false)
        private String state;

        @Column(nullable = false)
        private String product; // Laptop, Desktop, Printer, Workstation, Other

        @Column(nullable = false)
        private String serialNumber;

        @Column(nullable = false)
        private String brand;

        @Column(nullable = false)
        private String warranty; // Warranty, Non-Warranty, Other

        @Column(nullable = false)
        private String model;

        @Column(nullable = false)
        private String issueTitle;

        @Column(nullable = false)
        private String issueDetails;


    public Ticket() {
        this.createdAt = LocalDateTime.now();
        this.ticketId = generateTicketId();
    }

    private String generateTicketId() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyMMddHHmm");
        String dateTime = LocalDateTime.now().format(formatter);
        return dateTime;
    }
}