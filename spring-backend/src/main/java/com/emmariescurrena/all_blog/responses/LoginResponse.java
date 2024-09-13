package com.emmariescurrena.all_blog.responses;

import com.emmariescurrena.all_blog.dtos.UserDto;
import com.emmariescurrena.all_blog.models.User;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class LoginResponse extends UserDto {

    private String token;

    public LoginResponse(User user, String token) {
        super(user);
        this.setToken(token);
    } 

}
