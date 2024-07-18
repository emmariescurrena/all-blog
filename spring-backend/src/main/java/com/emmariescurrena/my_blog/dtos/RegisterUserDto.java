package com.emmariescurrena.my_blog.dtos;

import lombok.Data;

@Data
public class RegisterUserDto {
    private String email;

    private String password;

    private String name;

    private String surname;

}
