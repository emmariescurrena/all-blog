package com.emmariescurrena.all_blog.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.emmariescurrena.all_blog.dtos.LoginUserDto;
import com.emmariescurrena.all_blog.dtos.RegisterUserDto;
import com.emmariescurrena.all_blog.exceptions.EmailAlreadyExistsException;
import com.emmariescurrena.all_blog.exceptions.EmailNotRegisteredException;
import com.emmariescurrena.all_blog.models.Role;
import com.emmariescurrena.all_blog.models.RoleEnum;
import com.emmariescurrena.all_blog.models.User;
import com.emmariescurrena.all_blog.repositories.RoleRepository;
import com.emmariescurrena.all_blog.repositories.UserRepository;

@Service
public class AuthenticationService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    UserService userService;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    PasswordEncoder passwordEncoder;

    public User signup(RegisterUserDto registerUserDto) throws EmailAlreadyExistsException {
        Optional<Role> optionalRole = roleRepository.findByName(RoleEnum.USER);

        if (optionalRole.isEmpty()) {
            return null;
        }

        if (userService.isEmailRegistered(registerUserDto.getEmail())) {
            throw new EmailAlreadyExistsException("Email already registered: " + registerUserDto.getEmail());
        }

        User user = new User();

        user.setName(registerUserDto.getName());
        user.setSurname(registerUserDto.getSurname());
        user.setEmail(registerUserDto.getEmail());
        user.setPassword(passwordEncoder.encode(registerUserDto.getPassword()));
        user.setRole(optionalRole.get());

        return userRepository.save(user);
    }

    public User authenticate(LoginUserDto loginUserDto) throws EmailNotRegisteredException {
        if (!userService.isEmailRegistered(loginUserDto.getEmail())) {
            throw new EmailNotRegisteredException("Email already registered: " + loginUserDto.getEmail());
        }

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginUserDto.getEmail(),
                        loginUserDto.getPassword()
                )
        );

        return userRepository.findByEmail(loginUserDto.getEmail())
                .orElseThrow();
    }
}
