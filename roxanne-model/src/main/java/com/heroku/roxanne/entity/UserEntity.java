package com.heroku.roxanne.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Data
@AllArgsConstructor
public class UserEntity //implements UserDetails {
{
    @GeneratedValue
    @Id
    private Long id;
    private String name;

}
