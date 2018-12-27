package com.heroku.roxanne.security.service.api;

import com.heroku.roxanne.security.exception.RoleAlreadyExistException;
import com.heroku.roxanne.security.exception.RoleNotExistException;
import com.heroku.roxanne.security.model.UserIdentityRole;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface RoleService {

    List<UserIdentityRole> findAll();
    UserIdentityRole findByName(String name) throws RoleNotExistException;
    UserIdentityRole findById(Long id) throws RoleNotExistException;
    UserIdentityRole create(String authority) throws RoleAlreadyExistException;
    List<UserIdentityRole> create(List<String> authorities) throws RoleAlreadyExistException;
    UserIdentityRole update(Long id, String authority) throws RoleNotExistException;
    UserIdentityRole delete(Long id) throws RoleNotExistException;
}
