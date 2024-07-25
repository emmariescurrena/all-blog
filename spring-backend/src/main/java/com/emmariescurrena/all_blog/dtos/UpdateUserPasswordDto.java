package com.emmariescurrena.all_blog.dtos;

import com.emmariescurrena.all_blog.validators.ValidPassword;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class UpdateUserPasswordDto {

    @NotEmpty(message = "The new password is required")
    @ValidPassword
    private String newPassword;

    @NotEmpty(message = "Confirm new password must be equal to new password")
    private String confirmNewPassword;

    @AssertTrue(message = "Passwords do not match")
    private boolean isPasswordMatch() {
        return newPassword != null && newPassword.equals(confirmNewPassword);
    }

}
