package com.ynmio.School.Management.controller;

import com.ynmio.School.Management.model.Employee;
import com.ynmio.School.Management.model.EmployeeData.JobDetails;
import com.ynmio.School.Management.model.MyUser;
import com.ynmio.School.Management.model.Ticket;
import com.ynmio.School.Management.repository.EmployRepository;
import com.ynmio.School.Management.repository.EmployeeData.JobDetailsRepository;
import com.ynmio.School.Management.repository.MyUserRepository;
import com.ynmio.School.Management.repository.TicketRepository;
import com.ynmio.School.Management.service.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/tickets")
public class TicketController {
    @Autowired
    private TicketRepository ticketRepository;
    @Autowired
    private JwtUtil jwtUtils;
    @Autowired
    private JobDetailsRepository jobDetailsRepository;
    @Autowired
    private MyUserRepository myUserRepository;

    @PostMapping
    public ResponseEntity<String> createTicket(@RequestBody Ticket ticket) {
         ticket.setStatus("open");
         Ticket dbTicket =ticketRepository.save(ticket);
         return ResponseEntity.ok(dbTicket.getTicketId());
    }

    @GetMapping
    public List<Ticket> getAllTickets() {
        return ticketRepository.findAll();
    }

    @GetMapping("/{id}")
    public Ticket getTicketById(@PathVariable String id) {
        return ticketRepository.findByTicketId(id);
    }
    @GetMapping("/state")
    public ResponseEntity<?> getTicketByState(HttpServletRequest request) {
        String authorizationHeader = request.getHeader("Authorization");

        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Missing or invalid Authorization header");
        }

        try {
            String token = authorizationHeader.substring(7);
            String employeeEmail = jwtUtils.extractUsername(token);

            MyUser myUser = myUserRepository.findByEmail(employeeEmail);
            if (myUser == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
            }

            JobDetails jobDetails = jobDetailsRepository.findByUser(myUser);
            if (jobDetails == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("JobDetails not found for user: " + employeeEmail);
            }

            String state = jobDetails.getReportingBranch();
            List<Ticket> tickets = ticketRepository.findByState(state); // Ensure this method returns a list of tickets

            if (tickets == null || tickets.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No tickets found for state: " + state);
            }

            return ResponseEntity.ok(tickets);

        } catch (Exception e) {
            // Log the error for debugging
            // logger.error("Error processing request", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error processing request: " + e.getMessage());
        }
    }

    @PostMapping("/status/progress/{id}")
    public ResponseEntity<String> onProgress(HttpServletRequest request, @PathVariable Long id) {
        Optional<Ticket> optionalTicket = ticketRepository.findById(id);

        if (optionalTicket.isPresent()) {
            Ticket ticket = optionalTicket.get();
            ticket.setStatus("progress"); // Update the status field to "progress"
            ticketRepository.save(ticket); // Save the updated ticket

            return ResponseEntity.ok("Ticket status updated to progress");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Ticket not found");
        }
    }
    @PostMapping("/status/completed/{id}")
    public ResponseEntity<String> onCompleted(HttpServletRequest request, @PathVariable Long id) {
        Optional<Ticket> optionalTicket = ticketRepository.findById(id);

        if (optionalTicket.isPresent()) {
            Ticket ticket = optionalTicket.get();
            ticket.setStatus("Completed"); // Update the status field to "progress"
            ticketRepository.save(ticket); // Save the updated ticket

            return ResponseEntity.ok("Ticket status updated to Completed");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Ticket not found");
        }
    }


}


