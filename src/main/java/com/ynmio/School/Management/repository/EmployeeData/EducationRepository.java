package com.ynmio.School.Management.repository.EmployeeData;

import com.ynmio.School.Management.model.EmployeeData.Education;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EducationRepository extends JpaRepository<Education,Integer> {
}
