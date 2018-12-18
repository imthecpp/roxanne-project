package com.heroku.roxanne.security.service.impl;

import com.heroku.roxanne.security.entity.UserEntity;
import com.heroku.roxanne.security.exception.UserAlreadyExistException;
import com.heroku.roxanne.security.exception.UserNotExistException;
import com.heroku.roxanne.security.mapper.OrikaMapper;
import com.heroku.roxanne.security.model.UserIdentity;
import com.heroku.roxanne.security.model.api.UserIdentityApiModel;
import com.heroku.roxanne.security.repository.UserRepository;
import com.heroku.roxanne.security.service.api.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.validation.constraints.AssertFalse;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OrikaMapper orikaMapper;

    @Override
    public List<UserIdentity> findAll() {
        log.info("find all users");
        List<UserEntity> userEntities = userRepository.findAll();

        if (userEntities != null){
            log.info("returning list of users");
            return map(userEntities);
        } else {
            log.info("there is nothing to return");
            return null;
        }
    }

    @Override
    public UserIdentity findById(final Long id) throws UserNotExistException {
        log.info("find user with id: {} ", id);
        UserEntity userEntity = new UserEntity();
        if (id != null){
            userEntity = userRepository.findById(id)
                    .orElseThrow(() ->
                            new UserNotExistException("user with id: " + id + " not exist")
                    );
        }
        return map(userEntity);
    }

    @Override
    public UserIdentity findByUsername(final String username) throws UserNotExistException {
        return null;
    }

    @Override
    public UserEntity update(Long id, UserIdentityApiModel userIdentityApiModel) throws UserNotExistException {
        return null;
    }

    @Override
    public UserEntity create(UserIdentityApiModel userIdentityApiModel) throws UserAlreadyExistException {
        return null;
    }

    @Override
    public UserEntity delete(Long id) throws UserNotExistException {
        return null;
    }

    private List<UserIdentity> map(Collection<UserEntity> userEntity){
        return orikaMapper.getMapperFacade().mapAsList(userEntity, UserIdentity.class);
    }

    private UserIdentity map(UserEntity userEntity){
        return orikaMapper.getMapperFacade().map(userEntity, UserIdentity.class);
    }

}
