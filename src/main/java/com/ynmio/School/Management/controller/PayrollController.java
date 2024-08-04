package com.ynmio.School.Management.controller;

import com.ynmio.School.Management.model.Payroll;
import com.ynmio.School.Management.service.JwtUtil;
import com.ynmio.School.Management.service.PayrollService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/payroll")
public class PayrollController {

    @Autowired
    private PayrollService payrollService;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping
    @PreAuthorize("hasAnyRole('HR', 'ADMIN')")
    public ResponseEntity<Payroll> createPayroll(@RequestBody Payroll payroll) {
        Payroll savedPayroll = payrollService.savePayroll(payroll);
        return ResponseEntity.ok(savedPayroll);
    }

    @GetMapping
    public ResponseEntity<List<Payroll>> getPayrolls(HttpServletRequest request,
                                                     @RequestParam int year,
                                                     @RequestParam int month) {
        String token = request.getHeader("Authorization").substring(7);
        String employeeEmail = jwtUtil.extractUsername(token);
        List<Payroll> payrolls = payrollService.getPayrollsByEmailAndMonth(employeeEmail, year, month);
        return ResponseEntity.ok(payrolls);
    }
}

