package com.ynmio.School.Management.service;

import com.ynmio.School.Management.model.LeaveRequestForm;
import com.ynmio.School.Management.repository.LeaveRepository;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LeaveService {

    @Autowired
    private LeaveRepository leaveRepository;

    @Autowired
    private EmailSenderService emailSenderService;

    public void processLeaveRequest(LeaveRequestForm leaveRequestForm) {
        // Example: Business logic to process the leave request
        System.out.println("Leave Type: " + leaveRequestForm.getLeaveType());
        System.out.println("Start Date: " + leaveRequestForm.getStartDate());
        System.out.println("End Date: " + leaveRequestForm.getEndDate());
        System.out.println("Reason: " + leaveRequestForm.getReason());

        leaveRepository.save(leaveRequestForm);
        // Implement your logic to save the leave request to a database or perform other operations
        // For demonstration, printing the data to console
    }
    public List<LeaveRequestForm> getAllLeaveRequests() {
        return leaveRepository.findAll();
    }
    public List<LeaveRequestForm> findLeaveRequestsWithNullStatus() {
        return leaveRepository.findByStatusIsNull();
    }

    public void approveLeaveRequest(Integer leaveId,String role) {
        LeaveRequestForm leaveRequest = leaveRepository.findById(leaveId)
                .orElseThrow(() -> new RuntimeException("Leave request not found"));
        String email=leaveRequest.getEmpEmail();
        emailSenderService.sentEmail(email,
                " Approved " + email +" \nSkypro Technology","hii "+ email+"\nYour leave has beeb " +
                        "approved by "+role);
        leaveRequest.setStatus("Approved");
        leaveRepository.save(leaveRequest);
    }

    public void declineLeaveRequest(Integer leaveId, String role) {
        LeaveRequestForm leaveRequest = leaveRepository.findById(leaveId)
                .orElseThrow(() -> new RuntimeException("Leave request not found"));
        String email=leaveRequest.getEmpEmail();
        emailSenderService.sentEmail(email,
                " Declined " + email +" \nSkypro Technology","hii "+ email+"\nYour leave has been " +
                        "Declined by "+role);
        leaveRequest.setStatus("Declined");
        leaveRepository.save(leaveRequest);
    }
}

