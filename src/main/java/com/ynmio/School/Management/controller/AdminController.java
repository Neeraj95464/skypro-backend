package com.ynmio.School.Management.controller;

import com.ynmio.School.Management.model.BranchPresence;
import com.ynmio.School.Management.service.impl.AdminNormalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/branch-presence")
public class AdminController {

    @Autowired
    private AdminNormalService service;

    @GetMapping("/today")
    public List<BranchPresence> getTodayPresence() {
        String today = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        return service.getTodayPresence(today);
    }
    @PostMapping("/add")
    public BranchPresence addPresence(@RequestBody BranchPresence presence) {
        presence.setDate(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
        return service.addPresence(presence);
    }
}
