package com.heroku.roxanne.security.service.api;

import com.heroku.roxanne.security.entity.UserEntity;
import com.heroku.roxanne.security.exception.UserAlreadyExistException;
import com.heroku.roxanne.security.exception.UserNotExistException;
import com.heroku.roxanne.security.model.UserIdentity;
import com.heroku.roxanne.security.model.api.UserIdentityApiModel;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;
import java.util.Optional;

public interface UserService {


    List<UserIdentity> findAll();
    UserIdentity findById(Long id) throws UserNotExistException;
    UserIdentity findByUsername(String username) throws UsernameNotFoundException;
    UserIdentity update(Long id, UserIdentityApiModel userIdentityApiModel) throws UserNotExistException;
    Optional<UserIdentity> create(UserIdentityApiModel userIdentityApiModel) throws UserAlreadyExistException;
    UserIdentity delete(Long id) throws UserNotExistException;
}
