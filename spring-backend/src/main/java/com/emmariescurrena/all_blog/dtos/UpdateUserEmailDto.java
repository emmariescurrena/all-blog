package com.emmariescurrena.all_blog.dtos;

import com.emmariescurrena.all_blog.util.RegexValidator;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class UpdateUserEmailDto {

    @NotEmpty(message = "The new email is required")
    @Email(regexp = RegexValidator.EMAIL,
            flags = Pattern.Flag.CASE_INSENSITIVE,
            message = "Invalid email format")
    private String newEmail;

}
