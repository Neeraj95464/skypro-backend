package com.ynmio.School.Management.repository;

import com.ynmio.School.Management.model.ManagerData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ManagerDataRepository extends JpaRepository<ManagerData,Integer> {
}
