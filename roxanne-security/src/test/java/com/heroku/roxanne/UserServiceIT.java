package com.heroku.roxanne;

import com.heroku.roxanne.security.exception.UserNotExistException;
import com.heroku.roxanne.security.exception.UserValidationException;
import com.heroku.roxanne.security.exception.UserAlreadyExistException;
import com.heroku.roxanne.security.model.UserIdentity;
import com.heroku.roxanne.security.service.api.UserService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK, classes = {SecurityServiceTestConfig.class})
@ActiveProfiles( {"test"})
@AutoConfigureTestDatabase(replace= AutoConfigureTestDatabase.Replace.NONE)
@DirtiesContext
public class UserServiceIT {

    @Autowired
    UserService userService;

    @Test
    public void getOneByIdTest() throws UserNotExistException{
        UserIdentity userIdentity = userService.findById(1L);
        Assert.assertEquals(1, userIdentity.getId().longValue());
    }

    @Test(expected = UserNotExistException.class)
    public void getOneByIdNotExistTest() throws UserNotExistException{
        userService.findById(1L);
    }


    @Test
    public void getOneByUserNameTest(){

    }

    @Test
    public void getOneByUserNameNotExistTest(){

    }

    @Test
    public void getByRoleNameTest(){

    }

    @Test
    public void getByRoleNameEmptyNullTest(){

    }

    @Test
    public void getAllUsersTest(){
        List<UserIdentity> userIdentity = userService.findAll();
        Assert.assertFalse(userIdentity.isEmpty());
    }

    @Test
    public void getAllUsersEmptyNull(){
        List<UserIdentity> userIdentity = userService.findAll();
        Assert.assertTrue(userIdentity.isEmpty());
    }


    @Test
    public void getAllUsersPageableTest(){

    }

    @Test
    public void createUserTest(){

    }

    @Test(expected = UserAlreadyExistException.class)
    public void userAlreadyExistExceptionTest(){

    }

    @Test(expected = UserValidationException.class)
    public void createUserValidationExceptionTest(){

    }
    @Test
    public void updateUserTest(){

    }


    @Test(expected = UserValidationException.class)
    public void updateValidationException(){

    }

    @Test
    public void deleteUserTest(){

    }

    @Test(expected = UserNotExistException.class)
    public void deleteUserNotExistException(){

    }
}
