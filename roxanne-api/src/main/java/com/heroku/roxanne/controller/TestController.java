package com.heroku.roxanne.controller;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.spring.web.plugins.Docket;

@RestController
@Slf4j
@Api(value = "Test for web layer of application")
public class TestController {

    @Autowired
    private Docket docket;

    @ApiOperation(value = "test of the controller")
    @RequestMapping(value = "/hello", method = RequestMethod.GET, consumes = "application/json")
    public String getHello(){
    log.info("moje {} ", docket.isEnabled());

        return "index";

    }
}
