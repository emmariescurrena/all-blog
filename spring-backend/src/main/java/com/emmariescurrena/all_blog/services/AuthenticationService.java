package com.emmariescurrena.all_blog.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.emmariescurrena.all_blog.dtos.LoginUserDto;
import com.emmariescurrena.all_blog.dtos.RegisterUserDto;
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
    AuthenticationManager authenticationManager;

    @Autowired
    PasswordEncoder passwordEncoder;

    public User signup(RegisterUserDto input) {
        Optional<Role> optionalRole = roleRepository.findByName(RoleEnum.USER);

        if (optionalRole.isEmpty()) {
            return null;
        }

        User user = new User();

        user.setName(input.getName());
        user.setSurname(input.getSurname());
        user.setEmail(input.getEmail());
        user.setPassword(passwordEncoder.encode(input.getPassword()));
        user.setRole(optionalRole.get());

        return userRepository.save(user);
    }

    public User authenticate(LoginUserDto input) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        input.getEmail(),
                        input.getPassword()
                )
        );

        return userRepository.findByEmail(input.getEmail())
                .orElseThrow();
    }
}
