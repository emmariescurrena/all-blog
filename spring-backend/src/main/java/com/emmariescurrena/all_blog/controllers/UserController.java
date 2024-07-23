package com.emmariescurrena.all_blog.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;


import com.emmariescurrena.all_blog.dtos.UpdateEmailDto;
import com.emmariescurrena.all_blog.dtos.UpdatePasswordDto;
import com.emmariescurrena.all_blog.models.User;
import com.emmariescurrena.all_blog.services.UserService;
import static com.emmariescurrena.all_blog.util.ControllerHelperFunctions.getCurrentUser;

@RestController
@RequestMapping("/users")
@PreAuthorize("isAuthenticated()")
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping("/me")
    public ResponseEntity<User> currentUser() {
        return ResponseEntity.ok(getCurrentUser());
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUser(@PathVariable Long id) {
        return ResponseEntity.of(userService.getUserById(id));
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN')")
    public ResponseEntity<List<User>> allUsers() {
        return ResponseEntity.ok(userService.allUsers());
    }

    @PatchMapping("/me/email")
    public ResponseEntity<User> updateCurrentUserEmail(@RequestBody UpdateEmailDto updateEmailDto) {
        Long currentUserId = getCurrentUser().getId();

        return ResponseEntity.ok(userService.updateUserEmail(currentUserId, updateEmailDto));
    }

    @PatchMapping("/me/password")
    public ResponseEntity<User> updateCurrentUserPassword(@RequestBody UpdatePasswordDto updatePasswordDto) {
        Long currentUserId = getCurrentUser().getId();

        return ResponseEntity.ok(userService.updateUserPassword(currentUserId, updatePasswordDto));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('SUPER_ADMIN')")
    public ResponseEntity<User> deleteUser(@PathVariable Long id) {
        return ResponseEntity.ok(userService.deleteUser(id));
    }

    @DeleteMapping("/me")
    public ResponseEntity<User> deleteCurrentUser() {
        Long currentUserId = getCurrentUser().getId();

        return ResponseEntity.ok(userService.deleteUser(currentUserId));
    }

}
