package com.heroku.roxanne.repository;

import com.heroku.roxanne.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<Long, UserEntity> {
}
