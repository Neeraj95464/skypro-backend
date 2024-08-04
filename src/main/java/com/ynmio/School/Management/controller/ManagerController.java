package com.ynmio.School.Management.controller;

import com.ynmio.School.Management.model.EmployeeData.JobDetails;
import com.ynmio.School.Management.model.ManagerData;
import com.ynmio.School.Management.model.MyUser;
import com.ynmio.School.Management.repository.EmployeeData.JobDetailsRepository;
import com.ynmio.School.Management.repository.ManagerDataRepository;
import com.ynmio.School.Management.service.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/manager")
@CrossOrigin(origins = "http://localhost:5173")
public class ManagerController {

    @Autowired
    private JwtUtil jwtUtils;
    @Autowired
    private ManagerDataRepository managerDataRepository;
    @Autowired
    private JobDetailsRepository jobDetailsRepository;

    @PostMapping("/today-data")
    public ResponseEntity<ManagerData> enterData(@RequestBody ManagerData manager) {
        ManagerData savedManager = managerDataRepository.save(manager);
        return new ResponseEntity<>(savedManager, HttpStatus.CREATED);
    }

    @GetMapping("/employees")
    public ResponseEntity<List<MyUser>> getEmployeesByReportingBranch(HttpServletRequest request) {
        String token = jwtUtils.resolveToken(request);
        String email = jwtUtils.extractUsername(token);
        JobDetails managerJobDetails = jobDetailsRepository.findByUserEmail(email);

        if (managerJobDetails == null) {
            throw new RuntimeException("Manager job details not found");
        }

        String reportingBranch = managerJobDetails.getReportingBranch();
        List<JobDetails> jobDetailsList = jobDetailsRepository.findByReportingBranch(reportingBranch);
        List<MyUser> users = new ArrayList<>();

        for (JobDetails jobDetails : jobDetailsList) {
            users.add(jobDetails.getUser());
        }

        return ResponseEntity.ok(users);
    }


//        List<MyUser> users = userService.getUsersByReportingBranch(branch);
//        return ResponseEntity.ok(users);
//    }
}
