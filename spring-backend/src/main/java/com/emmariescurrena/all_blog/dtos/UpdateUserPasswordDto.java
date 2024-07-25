package com.emmariescurrena.all_blog.dtos;

import lombok.Data;

@Data
public class UpdateUserPasswordDto {
    private String oldPassword;

    private String newPassword;

    private String confirmNewPassword;
}
