package com.ynmio.School.Management.controller;

import com.ynmio.School.Management.model.EmployeeData.JobDetails;
import com.ynmio.School.Management.model.MyUser;
import com.ynmio.School.Management.repository.EmployeeData.JobDetailsRepository;
import com.ynmio.School.Management.service.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/hr")
public class HRController {

    @Autowired
    JobDetailsRepository jobDetailsRepository;
    @Autowired
    JwtUtil jwtUtil;

    @GetMapping("/employees")
    public ResponseEntity<List<MyUser>> getEmployeesByHr(HttpServletRequest request) {
        String token=jwtUtil.resolveToken(request);
        String hrEmail=jwtUtil.extractUsername(token);
        List<JobDetails> jobDetailsList = jobDetailsRepository.findByHrEmail(hrEmail);
        List<MyUser> users = new ArrayList<>();

        for (JobDetails jobDetails : jobDetailsList) {
            users.add(jobDetails.getUser());
        }
        return ResponseEntity.ok(users);
    }
}
