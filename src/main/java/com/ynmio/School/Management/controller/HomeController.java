package com.ynmio.School.Management.controller;

import com.ynmio.School.Management.model.JobApplication;
import com.ynmio.School.Management.model.ServiceBooking;
import com.ynmio.School.Management.repository.JobApplicationRepository;
import com.ynmio.School.Management.repository.ServiceBookingRepository;
import com.ynmio.School.Management.service.MyUserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("home")
@CrossOrigin(origins = "http://localhost:5173")
public class HomeController {

    @Autowired
    ServiceBookingRepository bookingRepository;

    @Autowired
    JobApplicationRepository jobApplicationRepository;

    @PostMapping("/service-book")
    public ResponseEntity<ServiceBooking> createBooking(@RequestBody ServiceBooking booking) {
        return ResponseEntity.ok(bookingRepository.save(booking));
    }

    @GetMapping("service-books")
    public ResponseEntity<List<ServiceBooking>> getAllBookings() {
        return ResponseEntity.ok(bookingRepository.findAll());
    }

    @PostMapping("job-application")
    public ResponseEntity<JobApplication> submitApplication(
            @RequestParam("name") String name,
            @RequestParam("email") String email,
            @RequestParam("phone") String phone,
            @RequestParam("position") String position,
            @RequestParam("resume") MultipartFile resume,
            @RequestParam("coverLetter") String coverLetter) {

        String userHome = System.getProperty("user.home");
        String filePath = userHome + "/Desktop/" + resume.getOriginalFilename();

        // Save the file to desktop
        try {
            resume.transferTo(new File(filePath));
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }

        JobApplication application = new JobApplication();
        application.setName(name);
        application.setEmail(email);
        application.setPhone(phone);
        application.setPosition(position);
        application.setResume(filePath); // Save the file path
        application.setCoverLetter(coverLetter);

        JobApplication savedApplication = jobApplicationRepository.save(application);
        return ResponseEntity.ok(savedApplication);
    }


}
