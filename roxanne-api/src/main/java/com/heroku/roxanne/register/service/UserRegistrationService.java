package com.heroku.roxanne.register.service;

import com.heroku.roxanne.security.exception.RoleNotExistException;
import com.heroku.roxanne.security.exception.UserAlreadyExistException;
import com.heroku.roxanne.security.model.UserIdentity;
import com.heroku.roxanne.security.model.api.UserIdentityApiModel;

public interface UserRegistrationService {

    UserIdentity register(UserIdentityApiModel userIdentityApiModel) throws UserAlreadyExistException, RoleNotExistException;
    //edit-user
    //
}
