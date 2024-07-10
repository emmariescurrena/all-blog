package com.emmariescurrena.my_blog.services;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.emmariescurrena.my_blog.models.User;
import com.emmariescurrena.my_blog.repositories.UserRepository;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not exists by Username");
        }

        Set<SimpleGrantedAuthority> authorities = new HashSet<>(Arrays.asList(new SimpleGrantedAuthority("user")));

        return new org.springframework.security.core.userdetails.User(username, user.getPwHash(), authorities);
    }
}
