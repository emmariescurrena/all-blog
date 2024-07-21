package com.emmariescurrena.my_blog.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.emmariescurrena.my_blog.dtos.RegisterUserDto;
import com.emmariescurrena.my_blog.dtos.UpdateEmailDto;
import com.emmariescurrena.my_blog.dtos.UpdatePasswordDto;
import com.emmariescurrena.my_blog.models.Role;
import com.emmariescurrena.my_blog.models.RoleEnum;
import com.emmariescurrena.my_blog.models.User;
import com.emmariescurrena.my_blog.repositories.RoleRepository;
import com.emmariescurrena.my_blog.repositories.UserRepository;


@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder passwordEncoder;
    
    public Optional<User> getUser(Long id) {
        return userRepository.findById(id);
    }

    public User updateUserEmail(User user, UpdateEmailDto updateEmailDto) {
        user.setEmail(updateEmailDto.getNewEmail());

        return userRepository.save(user);
    }

    public User updateUserPassword(User user, UpdatePasswordDto updatePasswordDto) {
        user.setPassword(passwordEncoder.encode(updatePasswordDto.getNewPassword()));

        return userRepository.save(user);
    }

    public List<User> allUsers() {
        List<User> users = new ArrayList<>();

        userRepository.findAll().forEach(users::add);

        return users;
    }

    public User createAdministrator(RegisterUserDto input) {
        Optional<Role> optionalRole = roleRepository.findByName(RoleEnum.ADMIN);

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
}
