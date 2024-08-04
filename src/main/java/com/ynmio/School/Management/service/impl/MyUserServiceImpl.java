package com.ynmio.School.Management.service.impl;

import com.ynmio.School.Management.model.MyUser;
import com.ynmio.School.Management.repository.MyUserRepository;
import com.ynmio.School.Management.service.MyUserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MyUserServiceImpl implements MyUserService {

    @Autowired
    private MyUserRepository repository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    @Cacheable("usersCache")
    public List<MyUser> getUsers() {
        System.out.println("calling find all method ");
        return repository.findAll();
    }

    @Override
    @Cacheable(value = "userCache", key = "#email")
    public Optional<MyUser> getUser(String email) {
        return Optional.ofNullable(repository.findByEmail(email));
    }

    @Override
    public void addUsers(List<MyUser> users) {
        repository.saveAll(users);
    }

    @Override
    public MyUser addUser(MyUser user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole("USER");
        return repository.save(user);
    }

    @Override
    public MyUser editUser(MyUser user, String email) {
        MyUser databaseUser = repository.findByEmail(email);
        BeanUtils.copyProperties(user, databaseUser, "email");
        repository.save(databaseUser);
        return databaseUser;
    }

    @Override
    public void deleteUser(String email) {
        MyUser user = repository.findByEmail(email);
        repository.delete(user);
    }

    @Override
    public void deleteAllUsers() {
        repository.deleteAll();
    }

    public MyUser findByUsername(String email) {
        return repository.findByEmail(email);
    }

    public boolean authenticate(String username, String password) {
        MyUser user = findByUsername(username);
        return user != null && passwordEncoder.matches(password, user.getPassword());
    }
}
