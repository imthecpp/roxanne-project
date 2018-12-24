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
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
        //List<UserIdentity> userEntities = userRepository.findAll();

        return Optional.of(userRepository.findAll())
                .map(uList -> {
                    log.info("returning list of users");
                    return map(uList);
                }).get();



//        if (userEntities != null) {
//            log.info("returning list of users");
//            return map(userEntities);
//        } else {
//            log.info("there is nothing to return");
//            return null;
//        }
    }

    @Override
    public UserIdentity findById(final Long id) throws UserNotExistException {
        log.info("find user with id: {} ", id);
        UserEntity userEntity = new UserEntity();
        if (id != null) {
            userEntity = userRepository.findById(id)
                    .orElseThrow(() ->
                            new UserNotExistException("user not exist")
                    );
        }
        log.info("founded user with id: {} ", userEntity.getId());
        return map(userEntity);
    }

    @Override
    public UserIdentity findByUsername(final String username) throws UsernameNotFoundException {
        log.info("find by username: {} ", username);
        UserEntity userEntity = Optional.of(username)
                .flatMap(usn -> userRepository.findByUsername(usn))
                .orElseThrow(() -> new UsernameNotFoundException("username not found"));
        return map(userEntity);
    }

    @Override
    public UserIdentity update(final Long id, UserIdentityApiModel userIdentityApiModel) throws UserNotExistException {

        if (id != null) {

        }
        return null;
    }

    @Override
    public Optional<UserIdentity> create(final UserIdentityApiModel userIdentityApiModel) throws UserAlreadyExistException {
        log.info("creating user");

        //field validation in controller layer then:
        //check if user exist by username and email
        //if not - create
        //if exist - throw exception
       UserEntity entity =  userRepository.findByUsername(userIdentityApiModel.getUsername()).get();

        Optional<UserIdentity> userIdentity = Optional.ofNullable(userIdentityApiModel)
                .map(userApi -> userRepository.findByUsername(userApi.getUsername())
                .filter(u -> !u.getUsername().equals(userApi.getUsername()))
                .map(u-> {
                    UserEntity userEntity = new UserEntity();
                    BeanUtils.copyProperties(u, userEntity, "passwordHash");
                    userRepository.save(userEntity);
                    return map(userEntity);
                })).orElseThrow(() -> new UserAlreadyExistException("user already exist"));

//        UserIdentity userIdentity = Optional.of(userIdentityApiModel)
//                .map(user -> userRepository.findByUsername(userIdentityApiModel.getUsername()))
//                .map(user -> {
//                    UserEntity userEntity = new UserEntity();
//                    BeanUtils.copyProperties(user, userEntity, "passwordHash");
//                    userRepository.save(userEntity);
//                    log.info("user with id: {} has been created", userEntity.getId());
//                    return map(userEntity);
//                }).orElseThrow(() -> new UserAlreadyExistException("user already exist"));

        return userIdentity;
    }

    @Override
    public UserIdentity delete(final Long id) throws UserNotExistException {
        log.info("deleting user");

        //flat map zwraca obiekt opakowany jako optional lub pusty optional
        //map, zwraca obiekt bez kontenera optional
        //ofNullable pomija defaultowe wyjątki
        UserEntity userEntity = Optional.ofNullable(id)
                .flatMap(userId -> {
                    log.info("looking for entity with id: {} ", userId);
                    return userRepository.findById(userId);
                }).orElseThrow(() -> new UserNotExistException("User not found"));

        return Optional.of(userEntity)
                .map(user -> {
                    userRepository.delete(user);
                    log.info("user with id: {} deleted", user.getId());
                    return map(user);
                }).orElseThrow(() -> new UserNotExistException("User not exist"));
    }

    private List<UserIdentity> map(Collection<UserEntity> userEntity) {
        return orikaMapper.getMapperFacade().mapAsList(userEntity, UserIdentity.class);
    }

    private UserIdentity map(UserEntity userEntity) {
        return orikaMapper.getMapperFacade().map(userEntity, UserIdentity.class);
    }

}
