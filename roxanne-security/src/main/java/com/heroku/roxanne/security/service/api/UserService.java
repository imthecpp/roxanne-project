package com.heroku.roxanne.security.service.api;

import com.heroku.roxanne.security.entity.UserEntity;
import org.springframework.stereotype.Service;

import java.util.List;

public interface UserService {


    List<UserEntity> findAll();
}
