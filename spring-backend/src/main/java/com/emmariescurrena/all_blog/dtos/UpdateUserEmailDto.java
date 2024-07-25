package com.emmariescurrena.all_blog.dtos;

import lombok.Data;

@Data
public class UpdateUserEmailDto {

    private String oldEmail;

    private String newEmail;

}
