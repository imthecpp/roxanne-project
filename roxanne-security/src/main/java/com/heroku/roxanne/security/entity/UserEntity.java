package com.heroku.roxanne.security.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.Collection;

@Entity
@Data
@AllArgsConstructor
public class UserEntity implements UserDetails {

    @GeneratedValue
    @Id
    private Long id;
    @Size(min = 3)
    private String username;
    @Size(min = 2)
    private String firstName;

    private String middleName;
    @Size(min = 2)
    private String lastName;

    @Column(nullable = false)
    private String Email;

    @Transient
    private String password;

    private String passwordHash;

    private boolean isAccountNonExpired;

    private boolean isAccountNonLocked;

    private boolean isCredentialsNonExpired;

    private boolean isEnabled;

    @ManyToMany
    @JoinTable(
            name = "USER_ROLE",
            joinColumns = {@JoinColumn(name = "USER_ID", referencedColumnName = "ID")},
            inverseJoinColumns = {@JoinColumn(name = "ROLE_ID", referencedColumnName = "ID")})
    private Collection<RoleEntity> authorities = new ArrayList<RoleEntity>();



}
