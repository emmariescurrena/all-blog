package com.emmariescurrena.all_blog.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.emmariescurrena.all_blog.dtos.RegisterUserDto;
import com.emmariescurrena.all_blog.dtos.UpdateUserDto;
import com.emmariescurrena.all_blog.models.Role;
import com.emmariescurrena.all_blog.models.RoleEnum;
import com.emmariescurrena.all_blog.models.User;
import com.emmariescurrena.all_blog.repositories.RoleRepository;
import com.emmariescurrena.all_blog.repositories.UserRepository;


@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder passwordEncoder;
    
    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    public Optional<User> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public boolean isEmailRegistered(String email) {
        return userRepository.existsByEmail(email);
    }

    public List<User> allUsers() {
        List<User> users = new ArrayList<>();

        userRepository.findAll().forEach(users::add);

        return users;
    }

    public void updateUser(User userToUpdate, UpdateUserDto updateUserDto) {

        userToUpdate.setEmail(updateUserDto.getEmail());
        userToUpdate.setPassword(passwordEncoder.encode(updateUserDto.getPassword()));

        userRepository.save(userToUpdate);
    }


    public void deleteUser(User user) {
        userRepository.delete(user);
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
