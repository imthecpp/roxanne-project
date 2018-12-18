package com.heroku.roxanne.security.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
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

    @CreatedDate
    private Date creationDate;

    @LastModifiedDate
    private Date lastModified;

    @Column(nullable = false)
    @Email
    private String email;

    @Transient
    private String password;

    private String passwordHash;

    private boolean isAccountNonExpired;

    private boolean isAccountNonLocked;

    private boolean isCredentialsNonExpired;
    
    private boolean isEnabled;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "USER_ROLE",
            joinColumns = {@JoinColumn(name = "USER_ID", referencedColumnName = "ID")},
            inverseJoinColumns = {@JoinColumn(name = "ROLE_ID", referencedColumnName = "ID")})
    private Collection<RoleEntity> authorities = new ArrayList<RoleEntity>();



}
