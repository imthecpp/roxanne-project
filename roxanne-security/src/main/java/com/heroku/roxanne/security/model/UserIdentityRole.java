package com.heroku.roxanne.security.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

@Data
@AllArgsConstructor
public class UserIdentityRole implements GrantedAuthority {

    private Long id;
    private String authority;

    @Override
    public String getAuthority() {
        return this.authority;
    }
}
