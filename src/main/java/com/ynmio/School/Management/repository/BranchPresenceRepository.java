package com.ynmio.School.Management.repository;

import com.ynmio.School.Management.model.BranchPresence;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BranchPresenceRepository extends JpaRepository<BranchPresence, Integer> {
    List<BranchPresence> findByDate(String date);
}
