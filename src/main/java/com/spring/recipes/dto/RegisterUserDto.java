package com.spring.recipes.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * The main data transfer object for user registration. Contains the bare minimum for a registration form.
 * @author Alex Giazitzis
 */
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Getter
public class RegisterUserDto {

    @Email(regexp = ".+@.+\\..+")
    String email;

    @NotBlank(message = "Password must not be empty.")
    @Size(min = 8, message = "Password must be at least 8 characters long.")
    String password;

}
