package com.java.roxanne.register.service;


import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.envers.repository.support.EnversRevisionRepositoryFactoryBean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

//@Configuration
@DataJpaTest
@EntityScan(basePackages = "com.heroku.roxanne")
@EnableJpaRepositories(repositoryFactoryBeanClass = EnversRevisionRepositoryFactoryBean.class, basePackages = "com.heroku.roxanne")
//@EnableGlobalMethodSecurity(securedEnabled = false, prePostEnabled = false)
//@ComponentScan(
//        basePackages="com.heroku.roxanne",
//        excludeFilters = {
//                @ComponentScan.Filter(type = ASSIGNABLE_TYPE,
//                        value = {
//                                SessionAutoConfiguration.class
//                        })
//        })
@ComponentScan(basePackages="com.heroku.roxanne")
public class SecurityServiceTestConfig {
}
