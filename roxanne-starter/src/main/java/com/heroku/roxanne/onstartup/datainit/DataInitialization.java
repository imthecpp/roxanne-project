package com.heroku.roxanne.onstartup.datainit;

import com.heroku.roxanne.security.entity.UserEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class DataInitialization implements ApplicationListener<ContextRefreshedEvent> {

//    @Autowired
//    UserRepository userRepository;
@Autowired
PasswordEncoder encoder;
    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
      log.info("------------moj event------------");

    }


    private void createDefaultUser(){
        UserEntity admin = new UserEntity();
        admin.setAccountNonExpired(Boolean.TRUE);
        admin.setAccountNonLocked(Boolean.TRUE);
        admin.setCredentialsNonExpired(Boolean.TRUE);
        admin.setEnabled(Boolean.TRUE);
        admin.setFirstName("admin");
        admin.setLastName("admin");
        admin.setPasswordHash(encoder.encode("admin"));//endocer
        admin.setEmail("roxanne@project-dev.com");


    }

    private void createRoleIfNecessary(){
        //TO-DO
    }
}
