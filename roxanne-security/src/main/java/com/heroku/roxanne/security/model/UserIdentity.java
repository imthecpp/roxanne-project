package com.heroku.roxanne.security.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserIdentity {
    protected Long id;
    @Size(min = 3, max = 30)
    protected String username;
    @Max(50)
    protected String firstName;
    @Max(50)
    protected String middleName;
    @Max(50)
    protected String lastName;
    @Max(50)
    @Email
    @NotBlank
    protected String email;

    @JsonIgnore
    @NotBlank
    protected String passwordHash;

    @JsonIgnore
    @NotBlank
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$")
    protected String password;

    protected boolean isAccountNonExpired = true;

    protected boolean isAccountNonLocked = true;

    protected boolean isCredentialsNonExpired = true;

    protected boolean isEnabled = true;

    protected Collection<UserIdentityRole> authorities;
    protected Map<String, String> attributes = new HashMap<>();
}
