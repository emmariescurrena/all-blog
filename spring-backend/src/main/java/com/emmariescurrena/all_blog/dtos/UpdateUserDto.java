package com.emmariescurrena.all_blog.dtos;

import com.emmariescurrena.all_blog.util.RegexValidator;
import com.emmariescurrena.all_blog.validators.ValidPassword;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class UpdateUserDto {

    @NotEmpty(message = "The email is required")
    @Email(regexp = RegexValidator.EMAIL,
            flags = Pattern.Flag.CASE_INSENSITIVE,
            message = "Invalid email format")
    private String email;

    @NotEmpty(message = "The password is required")
    @ValidPassword
    private String password;

    @NotEmpty(message = "Confirm password must be equal to password")
    private String confirmPassword;

    @AssertTrue(message = "Passwords do not match")
    private boolean isPasswordMatch() {
        return password != null && password.equals(confirmPassword);
    }

}
