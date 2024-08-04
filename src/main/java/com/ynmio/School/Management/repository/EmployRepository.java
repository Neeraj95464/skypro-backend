package com.ynmio.School.Management.repository;

import com.ynmio.School.Management.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployRepository extends JpaRepository<Employee,Integer> {
    Employee findByEmail(String email);
}
