package com.heroku.roxanne.security.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserIdentity {
    protected Long id;

    protected String username;

    protected String firstName;

    protected String middleName;

    protected String lastName;

    protected String email;

    @JsonIgnore
    protected String passwordHash;

    @JsonIgnore
    protected String password;

    protected boolean isAccountNonExpired = true;

    protected boolean isAccountNonLocked = true;

    protected boolean isCredentialsNonExpired = true;

    protected boolean isEnabled = true;

    protected Collection<UserIdentityRole> authorities;
    protected Map<String, String> attributes = new HashMap<>();
}
