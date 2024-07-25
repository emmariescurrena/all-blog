package com.emmariescurrena.all_blog.dtos;

import org.springframework.beans.factory.annotation.Autowired;

import com.emmariescurrena.all_blog.services.UserService;

import jakarta.validation.constraints.AssertTrue;
import lombok.Data;

@Data
public class LoginUserDto {

    @Autowired
    UserService userService;

    private String email;

    @AssertTrue(message = "Email is not registered")
    private boolean emailRegistered() {
        return userService.getUserByEmail(email).isPresent();
    }

    private String password;
}
