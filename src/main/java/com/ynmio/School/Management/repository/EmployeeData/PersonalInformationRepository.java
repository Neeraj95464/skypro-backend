package com.ynmio.School.Management.repository.EmployeeData;

import com.ynmio.School.Management.model.EmployeeData.PersonalInformation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PersonalInformationRepository extends JpaRepository<PersonalInformation, Integer> {
    Optional<PersonalInformation> findByEmail(String email);
}
