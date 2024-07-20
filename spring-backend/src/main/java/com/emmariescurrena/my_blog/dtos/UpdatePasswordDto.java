package com.emmariescurrena.my_blog.dtos;

import lombok.Data;

@Data
public class UpdatePasswordDto {

    private String oldPassword;

    private String newPassword;

    private String confirmNewPassword;

}
