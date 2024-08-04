package com.ynmio.School.Management.controller;

import com.ynmio.School.Management.model.Employee;
import com.ynmio.School.Management.model.EmployeeData.*;
import com.ynmio.School.Management.model.LeaveRequestForm;
import com.ynmio.School.Management.model.MyUser;
import com.ynmio.School.Management.repository.EmployRepository;
import com.ynmio.School.Management.repository.EmployeeData.*;
import com.ynmio.School.Management.repository.JobApplicationRepository;
import com.ynmio.School.Management.repository.MyUserRepository;
import com.ynmio.School.Management.service.JwtUtil;
import com.ynmio.School.Management.service.LeaveService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/employee")
public class EmpController
{

    @Autowired
    private LeaveService leaveService;

    @Autowired
    private MyUserRepository myUserRepository;

    @Autowired
    private JwtUtil jwtUtils;
    @Autowired
    Uploads uploads;

    @Autowired
    private EmployRepository employeeRepository;

    @Autowired
    private BankingInformationRepository bankingInformationRepository;
    @Autowired
    private ContactDetailsRepository contactDetailsRepository;
    @Autowired
    EducationRepository educationRepository;
    @Autowired
    JobDetailsRepository jobDetailsRepository;
    @Autowired
    PersonalInformationRepository personalInformationRepository;
    @Autowired
    UploadRepository uploadRepository;
    @Autowired
    WorkExperienceRepository workExperienceRepository;


        // Get all employees
        @GetMapping
        public List<Employee> getAllEmployees() {
            return employeeRepository.findAll();
        }

        // Get an employee by ID
        @GetMapping("/{id}")
        public ResponseEntity<Employee> getEmployeeById(@PathVariable int id) {
            Optional<Employee> employee = employeeRepository.findById(id);
            if (employee.isPresent()) {
                return ResponseEntity.ok(employee.get());
            } else {
                return ResponseEntity.notFound().build();
            }
        }

        // Update an existing employee
        @PutMapping("/{id}")
        public ResponseEntity<Employee> updateEmployee(@PathVariable int id, @RequestBody Employee employeeDetails) {
            Optional<Employee> employee = employeeRepository.findById(id);
            if (employee.isPresent()) {
                Employee existingEmployee = employee.get();
                existingEmployee.setName(employeeDetails.getName());
                existingEmployee.setEmail(employeeDetails.getEmail());
                existingEmployee.setHrEmail(employeeDetails.getHrEmail());
                existingEmployee.setReportingBranch(employeeDetails.getReportingBranch());
                existingEmployee.setHighestDegree(employeeDetails.getHighestDegree());
                existingEmployee.setUniversity(employeeDetails.getUniversity());
                existingEmployee.setYearOfPassing(employeeDetails.getYearOfPassing());
                existingEmployee.setSkills(employeeDetails.getSkills());
                existingEmployee.setCertifications(employeeDetails.getCertifications());
                existingEmployee.setFamilyDetails(employeeDetails.getFamilyDetails());
                Employee updatedEmployee = employeeRepository.save(existingEmployee);
                return ResponseEntity.ok(updatedEmployee);
            } else {
                return ResponseEntity.notFound().build();
            }
        }

        // Delete an employee
        @DeleteMapping("/{id}")
        public ResponseEntity<Void> deleteEmployee(@PathVariable int id) {
            Optional<Employee> employee = employeeRepository.findById(id);
            if (employee.isPresent()) {
                employeeRepository.delete(employee.get());
                return ResponseEntity.noContent().build();
            } else {
                return ResponseEntity.notFound().build();
            }
        }
        @GetMapping("/details")
        public MyUser getEmployeeDetails(HttpServletRequest request) {
            String token = jwtUtils.resolveToken(request);
            String username = jwtUtils.extractUsername(token);
            MyUser currentUser= myUserRepository.findByEmail(username);
            return currentUser;
        }

    @PostMapping("/leave-request")
    public ResponseEntity<String> submitLeaveRequest(HttpServletRequest request, @RequestBody LeaveRequestForm leaveRequestForm) {
        try {
            String token = jwtUtils.resolveToken(request);
            if (token == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body("JWT token is missing or invalid");
            }

            String username = jwtUtils.extractUsername(token);
            System.out.println("Submitted by: " + username);

            leaveRequestForm.setEmpEmail(username);

            leaveService.processLeaveRequest(leaveRequestForm);

            return ResponseEntity.ok("Leave request submitted successfully");
        } catch (MalformedJwtException | ExpiredJwtException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Invalid or expired JWT token: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to submit leave request: " + e.getMessage());
        }
    }
    @GetMapping("/leave-requests")
    public ResponseEntity<List<LeaveRequestForm>> getLeaveRequests() {
        List<LeaveRequestForm> leaveRequests = leaveService.getAllLeaveRequests();
        return ResponseEntity.ok(leaveRequests);
    }
    @GetMapping("/pending/leave-requests")
    public ResponseEntity<List<LeaveRequestForm>> getPendingLeaveRequests() {
        List<LeaveRequestForm> pendingLeaveRequests = leaveService.findLeaveRequestsWithNullStatus();
        return ResponseEntity.ok(pendingLeaveRequests);
    }

    @PostMapping("/leave-requests/approve/{leaveId}")
    public ResponseEntity<String> approveLeave(HttpServletRequest request,@PathVariable Integer leaveId) {
        String token=jwtUtils.resolveToken(request);
        String role=jwtUtils.extractRole(token);
        try {
            leaveService.approveLeaveRequest(leaveId,role);
            return ResponseEntity.ok("Leave request approved successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to approve leave request: " + e.getMessage());
        }
    }

    @PostMapping("/leave-requests/decline/{leaveId}")
    public ResponseEntity<String> declineLeave(HttpServletRequest request,@PathVariable Integer leaveId) {
        String token=jwtUtils.resolveToken(request);
        String role=jwtUtils.extractRole(token);
        try {
            leaveService.declineLeaveRequest(leaveId,role);
            return ResponseEntity.ok("Leave request declined successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to decline leave request: " + e.getMessage());
        }
    }

    @PostMapping("/personal-information")
    public ResponseEntity<String> savePersonalInformation(HttpServletRequest request, @RequestBody PersonalInformation personalInformation) {
        String token = jwtUtils.resolveToken(request);
        String myUserEmail = jwtUtils.extractUsername(token);

        // Check if the user already has personal information
        Optional<PersonalInformation> existingInfo = personalInformationRepository.findByEmail(myUserEmail);
        if (existingInfo.isPresent()) {
            return ResponseEntity.ok("Your data already exists");
        } else {
            MyUser user = myUserRepository.findByEmail(myUserEmail);
            if (user == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
            }
            personalInformation.setUser(user);
            personalInformationRepository.save(personalInformation);
            return ResponseEntity.ok("Personal Information saved successfully");
        }
    }


        @PostMapping("/job-details")
        public ResponseEntity<String> saveJobDetails(HttpServletRequest request,@RequestBody JobDetails jobDetails) {
            String token=jwtUtils.resolveToken(request);
            MyUser user=myUserRepository.findByEmail(jwtUtils.extractUsername(token));
            jobDetails.setUser(user);
            jobDetailsRepository.save(jobDetails);
            return ResponseEntity.ok("Job Details saved successfully");
        }

        @PostMapping("/contact-details")
        public ResponseEntity<String> saveContactDetails(HttpServletRequest request,@RequestBody ContactDetails contactDetails) {
            String token=jwtUtils.resolveToken(request);
            MyUser user=myUserRepository.findByEmail(jwtUtils.extractUsername(token));
            contactDetails.setUser(user);
            contactDetailsRepository.save(contactDetails);
            return ResponseEntity.ok("Contact Details saved successfully");
        }

        @PostMapping("/education")
        public ResponseEntity<String> saveEducation(HttpServletRequest request,@RequestBody Education education) {
            String token=jwtUtils.resolveToken(request);
            MyUser user=myUserRepository.findByEmail(jwtUtils.extractUsername(token));
            education.setUser(user);
            educationRepository.save(education);
            return ResponseEntity.ok("Education saved successfully");
        }

        @PostMapping("/work-experience")
        public ResponseEntity<String> saveWorkExperience(HttpServletRequest request,@RequestBody WorkExperience workExperience) {
            String token=jwtUtils.resolveToken(request);
            MyUser user=myUserRepository.findByEmail(jwtUtils.extractUsername(token));
            workExperience.setUser(user);
            workExperienceRepository.save(workExperience);
            return ResponseEntity.ok("Work Experience saved successfully");
        }

        @PostMapping("/banking-information")
        public ResponseEntity<String> saveBankingInformation(HttpServletRequest request,@RequestBody BankingInformation bankingInformation) {
            String token=jwtUtils.resolveToken(request);
            MyUser user=myUserRepository.findByEmail(jwtUtils.extractUsername(token));
            bankingInformation.setUser(user);
            bankingInformationRepository.save(bankingInformation);
            return ResponseEntity.ok("Banking Information saved successfully");
        }

    @PostMapping("/uploads")
    public ResponseEntity<String> saveUploads(HttpServletRequest request,@RequestParam("photo") MultipartFile photo,
                                              @RequestParam("resume") MultipartFile resume,
                                              @RequestParam("portfolio") MultipartFile portfolio) throws IOException {

        String photoPath = saveFileToDesktop(photo);
        String resumePath = saveFileToDesktop(resume);
        String portfolioPath = saveFileToDesktop(portfolio);

        String token=jwtUtils.resolveToken(request);
        MyUser user=myUserRepository.findByEmail(jwtUtils.extractUsername(token));
        uploads.setUser(user);
        uploads.setPhotoPath(photoPath);
        uploads.setResumePath(resumePath);
        uploads.setPortfolioPath(portfolioPath);
        uploadRepository.save(uploads);

        return ResponseEntity.ok("Uploads saved successfully");
    }

    private String saveFileToDesktop(MultipartFile file) throws IOException {
        String desktopPath = System.getProperty("user.home") + "/Desktop";
        String filePath = desktopPath + "/" + file.getOriginalFilename();
        Path path = Paths.get(filePath);

        // Ensure unique file name
        int counter = 1;
        while (Files.exists(path)) {
            String newFileName = file.getOriginalFilename().replaceFirst("(\\.\\w+)$", "_" + counter + "$1");
            filePath = desktopPath + "/" + newFileName;
            path = Paths.get(filePath);
            counter++;
        }

        Files.write(path, file.getBytes());
        return filePath;
    }


}