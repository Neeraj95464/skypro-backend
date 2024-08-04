package com.ynmio.School.Management.repository;

import com.ynmio.School.Management.model.Payroll;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PayrollRepository extends JpaRepository<Payroll,Long> {
    List<Payroll> findByEmployeeEmailAndYearAndMonth(String employeeEmail, int year, int month);
}
