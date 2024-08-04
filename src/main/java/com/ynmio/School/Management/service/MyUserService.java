package com.ynmio.School.Management.service;

import com.ynmio.School.Management.model.MyUser;

import java.util.List;
import java.util.Optional;

public interface MyUserService {

    List<MyUser> getUsers();
    Optional<MyUser> getUser(String email);
    void addUsers(List<MyUser> users);
    MyUser addUser(MyUser user);
    MyUser editUser(MyUser user,String email);
    void deleteUser(String email);
    void deleteAllUsers();

    boolean authenticate(String username, String password);
}
