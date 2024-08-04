package com.ynmio.School.Management.repository.EmployeeData;

import com.ynmio.School.Management.model.EmployeeData.WorkExperience;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WorkExperienceRepository extends JpaRepository<WorkExperience,Integer> {
}
