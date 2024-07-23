package com.emmariescurrena.all_blog.dtos;

import org.springframework.beans.factory.annotation.Autowired;

import com.emmariescurrena.all_blog.services.UserService;

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

    @AssertTrue(message = "Email already registered")
    private boolean emailNotRegistered() {
        return userService.getUserByEmail(email).isEmpty();
    }

    @NotEmpty(message = "The password is required")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[!@#$%^&*(),.?\":{}|<>])[A-Za-z\\d!@#$%^&*(),.?\":{}|<>]{8,20}$",
            message = """
                    Password must have at least one lowercased letter,
                    one uppercased letter, one number, one special character,
                    no whitespaces and length between 8 and 20,
                    """)
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
