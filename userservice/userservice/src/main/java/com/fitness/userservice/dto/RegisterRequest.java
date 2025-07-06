package com.fitness.userservice.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.Singular;
import org.aspectj.bridge.IMessage;

@Data
public class RegisterRequest {

   @NotBlank(message = "Email is required")
   @Email(message = "Invalid email formate")
    private String email;

    @NotBlank(message = "Password is required")
    @Size(min=6, message = "Password must have at 6 characters")
    private String password;
    @NotNull
    private String firstName;
    @NotNull
    private String lastName;
}
