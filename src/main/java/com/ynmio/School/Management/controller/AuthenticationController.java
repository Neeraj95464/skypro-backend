package com.ynmio.School.Management.controller;

import com.ynmio.School.Management.model.AuthenticationRequest;
import com.ynmio.School.Management.model.AuthenticationResponse;
import com.ynmio.School.Management.model.MyUser;
import com.ynmio.School.Management.repository.MyUserRepository;
import com.ynmio.School.Management.service.EmailSenderService;
import com.ynmio.School.Management.service.JwtUtil;
import com.ynmio.School.Management.service.MyUserService;
import com.ynmio.School.Management.service.impl.MyUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private EmailSenderService emailSenderService;

    @Autowired
    private MyUserService service;

    @Autowired
    private MyUserDetailsService myUserDetailsService;

    @Autowired
    private JwtUtil jwtTokenUtil;

    @Autowired
    private MyUserRepository repository;

    @PostMapping("/authenticate")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest) throws Exception {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword())
            );
        } catch (BadCredentialsException e) {
            throw new Exception("Incorrect username or password", e);
        }

        final UserDetails userDetails = myUserDetailsService.loadUserByUsername(authenticationRequest.getUsername());

        // Fetch the user role from the repository
        MyUser user = repository.findByEmail(authenticationRequest.getUsername());
        String role = user.getRole(); // Assuming the role is stored in the MyUser entity

        final String jwt = jwtTokenUtil.generateToken(userDetails, role);

        return ResponseEntity.ok(new AuthenticationResponse(jwt));
    }

    @PostMapping("/register")
    public ResponseEntity<MyUser> saveUser(@RequestBody MyUser user) {
        MyUser savedUser = service.addUser(user);
        String email=user.getEmail();
        emailSenderService.sentEmail(email,
                "Welcome to Skypro Technology","hii "+ email+"\n  Pleased to connect with " +
                        "you ");
        return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
    }
}
