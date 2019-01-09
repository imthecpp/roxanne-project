package com.heroku.roxanne.register.service.impl;

import com.heroku.roxanne.register.service.UserRegistrationService;
import com.heroku.roxanne.security.exception.RoleNotExistException;
import com.heroku.roxanne.security.exception.UserAlreadyExistException;
import com.heroku.roxanne.security.model.UserIdentity;
import com.heroku.roxanne.security.model.UserIdentityRole;
import com.heroku.roxanne.security.model.api.UserIdentityApiModel;
import com.heroku.roxanne.security.service.api.RoleService;
import com.heroku.roxanne.security.service.api.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;


@Service
@Slf4j
public class UserRegistrationServiceImpl implements UserRegistrationService {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @Override
    public UserIdentity register(UserIdentityApiModel userIdentityApiModel) throws UserAlreadyExistException, RoleNotExistException {


        if (userIdentityApiModel != null) {

            if (userIdentityApiModel.getAuthorities() != null) {
                Optional.of(roleService.findByName("ADMIN"))
                        .ifPresent(role -> userIdentityApiModel.getAuthorities().add(role));
                //handleAuthorities(userIdentityApiModel.getAuthorities());
            }

            if (checkPasswordConfirmation(userIdentityApiModel.getPassword(), userIdentityApiModel.getPasswordConfirmation())) {
                return userService.create(userIdentityApiModel);
            }
        }

        return null;
    }

    private boolean checkPasswordConfirmation(String password, String confirmation) {
        if (password.equals(confirmation)) {
            return true;
        }
        return false;
    }

    private void handleAuthorities(Collection<UserIdentityRole> authorities) throws RoleNotExistException {

        for (UserIdentityRole role : authorities) {
            roleService.findByName(role.getAuthority());
            //to-do
        }
    }
}
