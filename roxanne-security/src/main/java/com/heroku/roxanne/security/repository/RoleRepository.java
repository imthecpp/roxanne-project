package com.heroku.roxanne.security.repository;

import com.heroku.roxanne.security.entity.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<RoleEntity, Long> {
}
