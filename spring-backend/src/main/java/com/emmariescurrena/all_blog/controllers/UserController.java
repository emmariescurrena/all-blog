package com.emmariescurrena.all_blog.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;

import com.emmariescurrena.all_blog.dtos.UpdateUserDto;
import com.emmariescurrena.all_blog.dtos.UserDto;
import com.emmariescurrena.all_blog.exceptions.NotFoundException;
import com.emmariescurrena.all_blog.models.RoleEnum;
import com.emmariescurrena.all_blog.models.User;
import com.emmariescurrena.all_blog.services.UserService;

import jakarta.validation.Valid;

import static com.emmariescurrena.all_blog.util.ControllerHelperFunctions.getCurrentUser;

@RestController
@RequestMapping("/users")
@PreAuthorize("isAuthenticated()")
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping("/byId/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable Long id) {
        return Optional
            .ofNullable(userService.getUserById(id))
            .map( user -> ResponseEntity.ok(new UserDto(user.get())) )          //200 OK
            .orElseGet( () -> ResponseEntity.notFound().build() );
    }

    @GetMapping("/byEmail/{email}")
    public ResponseEntity<UserDto> getUserByEmail(@PathVariable String email) {
        return Optional
            .ofNullable(userService.getUserByEmail(email))
            .map( user -> ResponseEntity.ok(new UserDto(user.get())) )          //200 OK
            .orElseGet( () -> ResponseEntity.notFound().build() );
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN')")
    public ResponseEntity<List<User>> allUsers() {
        return ResponseEntity.ok(userService.allUsers());
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> updateUser(@PathVariable Long id, @Valid @RequestBody UpdateUserDto updateUserDto) {
        if (userService.getUserById(id).isEmpty()) {
            throw new NotFoundException("User for update not found");
        }

        User currentUser = getCurrentUser();

        if (!id.equals(currentUser.getId()) && !currentUser.getAuthorities().equals(List.of(RoleEnum.SUPER_ADMIN))) {
            throw new AccessDeniedException("You don't have the permission to update this user");
        }

        userService.updateUser(currentUser, updateUserDto);

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        if (userService.getUserById(id).isEmpty()) {
            throw new NotFoundException("You don't have the permission to delete this user");
        }

        User currentUser = getCurrentUser();

        if (!id.equals(currentUser.getId()) &&
                !currentUser.getAuthorities().equals(List.of(new SimpleGrantedAuthority("ROLE_SUPER_ADMIN")))) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        userService.deleteUser(currentUser);

        return ResponseEntity.ok().build();
    }

}
