package com.emmariescurrena.my_blog.dto;

public class SignUpDto {
    private String username;
    private String password;

    public SignUpDto() {

    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
