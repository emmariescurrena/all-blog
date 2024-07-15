package com.emmariescurrena.my_blog.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.emmariescurrena.my_blog.models.User;
import com.emmariescurrena.my_blog.repositories.UserRepository;
import com.emmariescurrena.my_blog.util.NotFoundException;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping("/users")
@CrossOrigin(origins = "http://localhost:4200")
public class UserController {
    @Autowired
    UserRepository userDAO;

    @GetMapping
    public List<User> findAllUsers() {
        return userDAO.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        Optional<User> user = userDAO.findById(id);

        if (user.isPresent()) {
            return ResponseEntity.ok(user.get());
        } else {
            throw new NotFoundException("Not found User by id: " + id);
        }
    }

    @PostMapping
    public User createUser(@RequestBody User user) {
        return userDAO.save(user);
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(
            @PathVariable Long id,
            @RequestBody User userUpdateData) {

        Optional<User> findUser = userDAO.findById(id);

        if (findUser.isPresent()) {
            User userToUpdate = findUser.get();
            userToUpdate.copyDataFromUser(userUpdateData);
            User userSaved = userDAO.save(userToUpdate);
            return ResponseEntity.ok(userSaved);
        } else {
            throw new NotFoundException("Not found User by id: " + id);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteUser(@PathVariable Long id) {
        Optional<User> findUser = userDAO.findById(id);

        if (findUser.isPresent()) {
            userDAO.delete(findUser.get());
            return ResponseEntity.ok(true);
        } else {
            throw new NotFoundException("Not found User by id: " + id);

        }
    }

}
