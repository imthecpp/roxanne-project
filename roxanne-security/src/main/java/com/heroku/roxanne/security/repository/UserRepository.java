package com.heroku.roxanne.security.repository;


import com.heroku.roxanne.security.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
}
