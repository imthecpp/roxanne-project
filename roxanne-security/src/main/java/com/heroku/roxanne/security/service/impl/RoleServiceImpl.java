package com.heroku.roxanne.security.service.impl;

import com.heroku.roxanne.security.entity.RoleEntity;
import com.heroku.roxanne.security.exception.RoleAlreadyExistException;
import com.heroku.roxanne.security.exception.RoleNotExistException;
import com.heroku.roxanne.security.mapper.OrikaMapper;
import com.heroku.roxanne.security.model.UserIdentityRole;
import com.heroku.roxanne.security.repository.RoleRepository;
import com.heroku.roxanne.security.service.api.RoleService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private OrikaMapper orikaMapper;


    @Override
    public List<UserIdentityRole> findAll() {
        log.info("returning all roles");

        List<RoleEntity> roleEntityList = roleRepository.findAll();

        if (roleEntityList != null) {
            return map(roleEntityList);
        }
        log.warn("list is empty");
        return null;
    }

    @Override
    public UserIdentityRole findByName(String name) throws RoleNotExistException {
        log.info("returning role by name");

        if (StringUtils.isNotEmpty(name)) {
            RoleEntity roleEntity = roleRepository.findByAuthority(name);

            if (roleEntity == null) {
                log.warn("can't find role by name: {}", name);
                throw new RoleNotExistException("Role not exist");
            }

            log.info("founded role with name: {}", name);
            return map(roleEntity);

        }

        log.warn("can't find role by name");
        return null;
    }

    @Override
    public UserIdentityRole findById(Long id) throws RoleNotExistException {
        log.info("returning role by id");

        if (id != null){
            RoleEntity roleEntity = roleRepository.findById(id).orElseThrow(() -> new RoleNotExistException("Role not found"));
            log.info("founded role with id: {}", id);

            return map(roleEntity);
        }

        log.warn("can't find role by id");
        return null;
    }

    @Override
    public List<UserIdentityRole> create(List<String> authorities) throws RoleAlreadyExistException {

//        return authorities.stream()
//                .map(authority -> this.create(authority))
//                .collect(Collectors.toList());
        return null;
    }

    @Override
    public UserIdentityRole create(String authority) throws RoleAlreadyExistException {
        log.info("creating new role");
        if (StringUtils.isNotEmpty(authority)){

            RoleEntity saved = roleRepository.findByAuthority(authority);

            if (saved != null){
                log.warn("role with authority: {} already exist", authority);
                throw new RoleAlreadyExistException("Role already exist");
            }

            log.info("created role with authority: {}", authority);
            RoleEntity newRole = new RoleEntity();
            newRole.setAuthority(authority);
            roleRepository.save(newRole);
            return map(newRole);

        }
        log.warn("can't create new role");
        return null;
    }

    @Override
    public UserIdentityRole update(Long id, String authority) throws RoleNotExistException {
        log.info("updating role");
        if (id != null){
            RoleEntity roleEntity = roleRepository.findById(id).orElseThrow(() -> new RoleNotExistException("role not exist"));

            Optional.ofNullable(authority)
                    .ifPresent(roleEntity::setAuthority);
            roleRepository.save(roleEntity);
            log.info("role with id: {} updated", id);
            return map(roleEntity);
        }

        log.warn("can't update role");
        return null;
    }

    @Override
    public UserIdentityRole delete(Long id) throws RoleNotExistException{

        if (id != null){
           RoleEntity roleEntity = roleRepository.findById(id).orElseThrow(() -> new RoleNotExistException("role not exist"));

           roleRepository.delete(roleEntity);
           return map(roleEntity);
        }
        return null;
    }

    private List<UserIdentityRole> map(Collection<RoleEntity> roleEntities) {
        return orikaMapper.getMapperFacade().mapAsList(roleEntities, UserIdentityRole.class);
    }

    private UserIdentityRole map(RoleEntity roleEntities) {
        return orikaMapper.getMapperFacade().map(roleEntities, UserIdentityRole.class);
    }
}
