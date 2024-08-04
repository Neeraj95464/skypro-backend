package com.ynmio.School.Management.repository;

import com.ynmio.School.Management.model.LeaveRequestForm;
import jdk.jfr.Registered;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LeaveRepository extends JpaRepository<LeaveRequestForm,Integer> {
    List<LeaveRequestForm> findByStatusIsNull();
}
