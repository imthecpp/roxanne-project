package com.heroku.roxanne.security.service.api;

import com.heroku.roxanne.security.entity.UserEntity;
import com.heroku.roxanne.security.exception.UserAlreadyExistException;
import com.heroku.roxanne.security.exception.UserNotExistException;
import com.heroku.roxanne.security.model.UserIdentity;
import com.heroku.roxanne.security.model.api.UserIdentityApiModel;

import java.util.List;

public interface UserService {


    List<UserIdentity> findAll();
    UserIdentity findById(final Long id) throws UserNotExistException;
    UserIdentity findByUsername(final String username) throws UserNotExistException;
    UserEntity update(final Long id, UserIdentityApiModel userIdentityApiModel) throws UserNotExistException;
    UserEntity create(UserIdentityApiModel userIdentityApiModel) throws UserAlreadyExistException;
    UserEntity delete(final Long id) throws UserNotExistException;
}
