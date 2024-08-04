package com.ynmio.School.Management.repository.EmployeeData;

import com.ynmio.School.Management.model.EmployeeData.JobDetails;
import com.ynmio.School.Management.model.MyUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JobDetailsRepository extends JpaRepository<JobDetails,Integer> {
    List<JobDetails> findByHrEmail(String hrEmail);
//    JobDetails findByEmail(String email);
    JobDetails findByUserEmail(String email);
    List<JobDetails> findByReportingBranch(String reportingBranch);
    JobDetails findByUser(MyUser user);
}
