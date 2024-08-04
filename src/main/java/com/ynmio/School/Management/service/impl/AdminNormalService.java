package com.ynmio.School.Management.service.impl;

import com.ynmio.School.Management.model.BranchPresence;
import com.ynmio.School.Management.repository.BranchPresenceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminNormalService{

    @Autowired
    private BranchPresenceRepository repository;

    public List<BranchPresence> getTodayPresence(String date) {
        return repository.findByDate(date);
    }
    public BranchPresence addPresence(BranchPresence presence) {
        return repository.save(presence);
    }
}
