package com.ynmio.School.Management.service;

import com.ynmio.School.Management.model.Payroll;
import com.ynmio.School.Management.repository.PayrollRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PayrollService {

    @Autowired
    private PayrollRepository payrollRepository;

    public Payroll savePayroll(Payroll payroll) {
        return payrollRepository.save(payroll);
    }

    public List<Payroll> getPayrollsByEmailAndMonth(String employeeEmail, int year, int month) {
        return payrollRepository.findByEmployeeEmailAndYearAndMonth(employeeEmail, year, month);
    }
}

