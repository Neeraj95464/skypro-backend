package com.ynmio.School.Management.service.impl;

import com.ynmio.School.Management.model.MyUser;
import com.ynmio.School.Management.repository.MyUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private MyUserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        MyUser user = userRepository.findByEmail(email);
        System.out.println("user email is "+email);
        System.out.println("user details is "+user);
        if (user == null) {
            throw new UsernameNotFoundException("No user found with email: " + email);
        }

        return new User(
                user.getEmail(),
                user.getPassword(),
                getRole(user)
        );
    }

    private Collection<? extends GrantedAuthority> getRole(MyUser user) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_" + user.getRole().toUpperCase()));
        return authorities;
    }
}

