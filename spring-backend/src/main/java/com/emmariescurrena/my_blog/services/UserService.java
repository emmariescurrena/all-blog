package com.emmariescurrena.my_blog.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.emmariescurrena.my_blog.models.User;
import com.emmariescurrena.my_blog.repositories.UserRepository;


@Service
public class UserService {

    @Autowired
    UserRepository userRepository;
    
    public List<User> allUsers() {
        List<User> users = new ArrayList<>();

        userRepository.findAll().forEach(users::add);

        return users;
    }
}
