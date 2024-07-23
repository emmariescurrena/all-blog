package com.emmariescurrena.all_blog.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.emmariescurrena.all_blog.dtos.RegisterUserDto;
import com.emmariescurrena.all_blog.models.User;
import com.emmariescurrena.all_blog.services.UserService;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/admins")
public class AdminController {
    
    @Autowired
    UserService userService;

    @PostMapping
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public ResponseEntity<User> createAdministrator(@RequestBody RegisterUserDto registerUserDto) {
        User createdAdmin = userService.createAdministrator(registerUserDto);
        
        return ResponseEntity.ok(createdAdmin);
    }
    

}
