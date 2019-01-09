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
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
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

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public List<UserIdentity> findAll() {
        log.info("find all users");
        List<UserEntity> userEntities = userRepository.findAll();

        if (userEntities != null) {
            log.info("returning list of users");
            return map(userEntities);
        } else {
            log.info("there is nothing to return");
            return null;
        }
    }

    @Override
    public UserIdentity findById(final Long id) throws UserNotExistException {
        log.info("find user");

        if (id == null) {
            log.error("id is null");
            return null;
        }

        UserEntity userEntity = userRepository.findById(id).orElseThrow(() -> new UserNotExistException("User not exist"));

        if (userEntity.getId() != null) {
            log.info("found user with id: {} ", userEntity.getId());
            return map(userEntity);
        }
        return null;
    }

    @Override
    public UserIdentity findByUsername(final String username) throws UsernameNotFoundException {
        log.info("find by username");

        if (StringUtils.isNoneEmpty(username)) {
            UserEntity userEntity = userRepository.findByUsername(username);
            if (userEntity != null) {
                log.info("find by username: {} ", userEntity.getUsername());
                return map(userEntity);
            }else {
                throw new UsernameNotFoundException("username not found exception");
            }
        }
        return null;
    }

    @Override
    @Transactional
    @Modifying
    public UserIdentity update(final Long id, UserIdentityApiModel userIdentityApiModel) throws UserNotExistException {

        if (id == null || userIdentityApiModel == null) {
            log.warn("can't update user");
            return null;
        }

        UserEntity entity = userRepository.findById(id).orElseThrow(() -> new UserNotExistException(""));

        if (entity.getId() != null){
            Optional.ofNullable(userIdentityApiModel.getEmail())
                    .ifPresent(entity::setEmail);
//            Optional.ofNullable(userIdentityApiModel.getAuthorities())
//                    .ifPresent(entity::setAuthorities);
            Optional.ofNullable(userIdentityApiModel.getFirstName())
                    .ifPresent(entity::setFirstName);
            Optional.ofNullable(userIdentityApiModel.getMiddleName())
                    .ifPresent(entity::setMiddleName);
            Optional.ofNullable(userIdentityApiModel.getLastName())
                    .ifPresent(entity::setLastName);
            Optional.ofNullable(userIdentityApiModel.getUsername())
                    .ifPresent(entity::setUsername);
            userRepository.save(entity);
            log.info("user with id: {} updated", entity.getId());
            return map(entity);
        }else {
            throw new UserNotExistException("user not exist");
        }
    }

    @Override
    @Transactional
    public UserIdentity create(final UserIdentityApiModel userIdentityApiModel) throws UserAlreadyExistException {
        log.info("creating user");

        //field validation in controller layer then:
        //check if user exist by username and email
        //if not - create and send email
        //if exist - throw exception
        UserEntity entity = userRepository.findByUsernameOrEmail(userIdentityApiModel.getUsername(), userIdentityApiModel.getEmail());

        if (entity != null) {
            log.warn("User with name: {} already exist", entity.getUsername());
            throw new UserAlreadyExistException("User already exist");
        }
        UserEntity toRegister = new UserEntity();
        userIdentityApiModel.setPasswordHash(passwordEncoder.encode(userIdentityApiModel.getPassword()));
        BeanUtils.copyProperties(userIdentityApiModel, toRegister, "password");

        entity = userRepository.save(toRegister);
        log.info("saved user with id: {} ", entity.getId());
        return map(entity);
    }

    @Override
    @Transactional
    public UserIdentity delete(final Long id) throws UserNotExistException {
        log.info("deleting user");

        if (id == null) {
            log.error("id is null");
            return null;
        }

        UserEntity entity = userRepository.findById(id).orElseThrow(() -> new UserNotExistException(""));
        if (entity.getId() != null) {
            userRepository.delete(entity);
            log.info("deleted user with id: {} ", entity.getId());
            return map(entity);
        }else {
            throw new UserNotExistException("can't delete user which not exist");
        }
    }

    private List<UserIdentity> map(Collection<UserEntity> userEntity) {
        return orikaMapper.getMapperFacade().mapAsList(userEntity, UserIdentity.class);
    }

    private UserIdentity map(UserEntity userEntity) {
        return orikaMapper.getMapperFacade().map(userEntity, UserIdentity.class);
    }

    private UserEntity map(UserIdentityApiModel apiModel) {
        return orikaMapper.getMapperFacade().map(apiModel, UserEntity.class);
    }

}
