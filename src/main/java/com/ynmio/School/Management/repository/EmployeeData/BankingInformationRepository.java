package com.ynmio.School.Management.repository.EmployeeData;

import com.ynmio.School.Management.model.EmployeeData.BankingInformation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BankingInformationRepository extends JpaRepository<BankingInformation,Integer> {
}
