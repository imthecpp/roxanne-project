package com.heroku.roxanne.onstartup.datainit;

import com.heroku.roxanne.security.entity.RoleEntity;
import com.heroku.roxanne.security.entity.UserEntity;
import com.heroku.roxanne.security.enumrole.RoleEnum;
import com.heroku.roxanne.security.repository.RoleRepository;
import com.heroku.roxanne.security.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
@Slf4j
class DataInitialization implements ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    PasswordEncoder encoder;
    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        log.info("------------------------------");
        log.info("------------------------------");
        log.info("------------------------------");
        log.info("-----Data Initialization------");
        log.info("------------------------------");
        log.info("------------------------------");
        log.info("------------------------------");

        //createRoleIfNecessary();
        createDefaultUser();

    }


    private void createDefaultUser(){
        UserEntity adminEntity = userRepository.findByUsername("admin");

        if (adminEntity == null){

            UserEntity admin = new UserEntity();
            RoleEntity roleEntity = createRoleIfNecessary(RoleEnum.ADMIN.name(), admin);

            admin.setAccountNonExpired(Boolean.TRUE);
            admin.setAccountNonLocked(Boolean.TRUE);
            admin.setCredentialsNonExpired(Boolean.TRUE);
            admin.setEnabled(Boolean.TRUE);
            admin.setFirstName("admin");
            admin.setLastName("admin");
            admin.setAuthorities(Arrays.asList(roleEntity));
            admin.setPasswordHash(encoder.encode("admin"));//endocer
            admin.setEmail("roxanne@project-dev.com");
            admin.setUsername("admin");
            userRepository.save(admin);
            log.info("------Admin added-------");
            log.info("-------Role added-------");

        } else{
            log.info("------Admin exist-------");

        }

    }

    private RoleEntity createRoleIfNecessary(String authority, UserEntity userEntity){
        //TO-DO
        RoleEntity role = roleRepository.findByAuthority(authority);
        if (role == null){
            RoleEntity roleEntity = new RoleEntity();
            roleEntity.setAuthority(authority);
            roleEntity.setUsers(Arrays.asList(userEntity));
            roleRepository.save(roleEntity);
            return roleEntity;
        }
        return role;
    }
}
