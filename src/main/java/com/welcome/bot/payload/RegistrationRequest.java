package com.welcome.bot.payload;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

public class RegistrationRequest {

    @NotBlank
    @Email
    private String email;

    @NotBlank
    private String role;

  
  

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
