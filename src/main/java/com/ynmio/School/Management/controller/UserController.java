package com.ynmio.School.Management.controller;

import com.ynmio.School.Management.model.MyUser;
import com.ynmio.School.Management.service.EmailSenderService;
import com.ynmio.School.Management.service.MyUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {

    private final MyUserService service;

    @Autowired
    public UserController(MyUserService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<MyUser>> getUsers() {
        List<MyUser> users = service.getUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("/{email}")
    public ResponseEntity<MyUser> getUser(@PathVariable String email) {
        Optional<MyUser> user = service.getUser(email);
        return user.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<MyUser> addUser(@RequestBody MyUser user) {
        service.addUser(user);
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    @PostMapping("/bulk")
    public ResponseEntity<List<MyUser>> addUsers(@RequestBody List<MyUser> users) {
        service.addUsers(users);
        return new ResponseEntity<>(users, HttpStatus.CREATED);
    }

    @DeleteMapping("/{email}")
    public ResponseEntity<Void> deleteUser(@PathVariable String email) {
        Optional<MyUser> user = service.getUser(email);
        if (user.isPresent()) {
            service.deleteUser(email);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{email}")
    public ResponseEntity<MyUser> editUser(@RequestBody MyUser user, @PathVariable String email) {
        try {
            MyUser updateUser = service.editUser(user, email);
            return new ResponseEntity<>(updateUser, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @DeleteMapping("/delete-all")
    public ResponseEntity<Void> deleteAll(){
        service.deleteAllUsers();
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
