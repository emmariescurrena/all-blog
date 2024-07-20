package com.emmariescurrena.my_blog.dtos;

import lombok.Data;

@Data
public class UpdateEmailDto {

    private String oldEmail;

    private String newEmail;

}
