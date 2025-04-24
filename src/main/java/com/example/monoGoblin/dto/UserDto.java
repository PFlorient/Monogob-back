package com.example.monoGoblin.dto;


import com.example.monoGoblin.model.UserModel;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public class UserDto {

    @NotBlank
    @Pattern(
            regexp = "^[^\\s]{4,}$",
            message = "Le pseudo doit faire au minimum 4 caractères"
    )
    private String username;

    @NotBlank
    @Pattern(
            regexp = "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{8,}$",
            message = "Le mot de passe doit avoir au moins 1 majuscule, 1 minuscule, 1 caractère spécial, 1 chiffre et doit faire au minimum 8 caractère"
    )
    private String password;

    @NotBlank
    @Email
    private String email;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public UserModel transform() {
        UserModel user = new UserModel();

        user.setUsername(this.getUsername() != null ? this.getUsername() : null);
        user.setEmail(this.getEmail() != null ? this.getEmail() : null);
        user.setPassword(this.getPassword() != null ? this.getPassword() : null);

        return user;
    }
}
