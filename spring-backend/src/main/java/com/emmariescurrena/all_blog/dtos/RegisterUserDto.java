package com.emmariescurrena.all_blog.dtos;

import org.springframework.beans.factory.annotation.Autowired;

import com.emmariescurrena.all_blog.services.UserService;
import com.emmariescurrena.all_blog.validators.ValidPassword;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RegisterUserDto {
    
    @Autowired
    UserService userService;

    @NotEmpty(message = "The email is required")
    @Email(regexp = "[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,3}",
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

    @NotEmpty(message = "The name is required")
    @Size(min = 2, max = 100, message = "The length of name must be between 2 and 100 characters")
    private String name;

    @NotEmpty(message = "The surname is required")
    @Size(min = 2, max = 100, message = "The length of surname must be between 2 and 100 characters")
    private String surname;

}
