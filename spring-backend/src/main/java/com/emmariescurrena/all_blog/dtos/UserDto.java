 package com.emmariescurrena.all_blog.dtos;

import com.emmariescurrena.all_blog.models.User;

import lombok.Data;

@Data
public class UserDto {

    private Long id;
    private String email;
    private String name;
    private String surname;
    private String role;

    public UserDto(User user) {
        this.id = user.getId();
        this.email = user.getEmail();
        this.name = user.getName();
        this.surname = user.getSurname();
        this.role = user.getRole().getName().toString();
    }
}
