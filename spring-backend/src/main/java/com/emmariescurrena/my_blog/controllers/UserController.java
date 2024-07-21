package com.emmariescurrena.my_blog.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.emmariescurrena.my_blog.dtos.UpdateEmailDto;
import com.emmariescurrena.my_blog.dtos.UpdatePasswordDto;
import com.emmariescurrena.my_blog.models.User;
import com.emmariescurrena.my_blog.services.UserService;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/users")
@PreAuthorize("isAuthenticated()")
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping("/me")
    public ResponseEntity<User> authenticatedUser() {
        User currentUser = getCurrentUser();

        return ResponseEntity.ok(currentUser);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getCurrentUser(@PathVariable Long id) {
        Optional<User> user = userService.getUser(id);

        return ResponseEntity.of(user);
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN')")
    public ResponseEntity<List<User>> allUsers() {
        List<User> users = userService.allUsers();

        return ResponseEntity.ok(users);
    }

    @PatchMapping("/me/email")
    public ResponseEntity<User> updateCurrentUserEmail(@RequestBody UpdateEmailDto updateEmailDto) {
        User currentUser = getCurrentUser();

        User updatedUser = userService.updateUserEmail(currentUser.getId(), updateEmailDto);
        return ResponseEntity.ok(updatedUser);
    }

    @PatchMapping("/me/password")
    public ResponseEntity<User> updateCurrentUserPassword(@RequestBody UpdatePasswordDto updatePasswordDto) {
        User currentUser = getCurrentUser();

        User updatedUser = userService.updateUserPassword(currentUser.getId(), updatePasswordDto);
        return ResponseEntity.ok(updatedUser);
    }

    @DeleteMapping("/me")
    public ResponseEntity<User> deleteUser() {
        User currentUser = getCurrentUser();

        userService.deleteUser(currentUser.getId());
        return ResponseEntity.ok(currentUser);
    }

    private User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return (User) authentication.getPrincipal();
    }

}
