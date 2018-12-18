package com.heroku.roxanne.security.service.impl;

import com.heroku.roxanne.security.entity.UserEntity;
import com.heroku.roxanne.security.repository.UserRepository;
import com.heroku.roxanne.security.service.api.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<UserEntity> findAll() {
        return userRepository.findAll();
    }
}
