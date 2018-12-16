package com.heroku.roxanne;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan(basePackages = {"com.heroku.roxanne"})
@EnableJpaRepositories(basePackages = {"com.heroku.roxanne"})
//@Import({WebConfig.class})
public class RoxanneProjectStarter {

    public static void main(String[] args){
        SpringApplication.run(RoxanneProjectStarter.class, args);

    }
}
