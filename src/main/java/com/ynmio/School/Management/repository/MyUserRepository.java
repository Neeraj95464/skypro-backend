package com.ynmio.School.Management.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.ynmio.School.Management.model.MyUser;

@Repository
public interface MyUserRepository extends JpaRepository<MyUser, Long> {
    MyUser findByEmail(String email);
}

