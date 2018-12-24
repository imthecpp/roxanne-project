package com.heroku.roxanne;

import com.heroku.roxanne.security.exception.UserNotExistException;
import com.heroku.roxanne.security.exception.UserValidationException;
import com.heroku.roxanne.security.exception.UserAlreadyExistException;
import com.heroku.roxanne.security.model.UserIdentity;
import com.heroku.roxanne.security.model.api.UserIdentityApiModel;
import com.heroku.roxanne.security.service.api.UserService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;


import java.util.List;
import java.util.Optional;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK, classes = {SecurityServiceTestConfig.class})
@ActiveProfiles( {"test"})
@AutoConfigureTestDatabase(replace= AutoConfigureTestDatabase.Replace.NONE)
@DirtiesContext
public class UserServiceIT {

    @Autowired
    private UserService userService;

    @Test
    public void getOneByIdTest() throws UserNotExistException{
        UserIdentity userIdentity = userService.findById(1L);
        Assert.assertEquals(1, userIdentity.getId().longValue());
    }

    @Test(expected = UserNotExistException.class)
    public void getOneByIdNotExistTest() throws UserNotExistException{
        userService.findById(2L);
    }


    @Test
    public void getOneByUserNameTest() throws UsernameNotFoundException {
        UserIdentity userIdentity = userService.findByUsername("admin");
        Assert.assertEquals("admin", userIdentity.getUsername());

    }

    @Test(expected = UsernameNotFoundException.class)
    public void getOneByUserNameNotFoundTest() throws UsernameNotFoundException {
        userService.findByUsername("adminNotFound");
    }

    @Test
    public void getByRoleNameTest(){

    }

    @Test
    public void getByRoleNameEmptyNullTest(){

    }

    @Test
    public void getAllUsersTest() {
        List<UserIdentity> userIdentity = userService.findAll();
        Assert.assertFalse(userIdentity.isEmpty());
    }

    @Test
    public void getAllUsersEmptyNull() {
        List<UserIdentity> userIdentity = userService.findAll();
        Assert.assertTrue(userIdentity.isEmpty());
    }


    @Test
    public void getAllUsersPageableTest(){
        Pageable pageable = new PageRequest(1, 1, Sort.Direction.ASC);
        //service
        Assert.assertEquals(1, pageable.getPageSize());
    }

    @Test
    public void createUserTest() throws UserAlreadyExistException{
        UserIdentityApiModel userIdentityApiModel = new UserIdentityApiModel();
        userIdentityApiModel.setAccountNonExpired(true);
        userIdentityApiModel.setAccountNonLocked(true);
        userIdentityApiModel.setCredentialsNonExpired(true);
        userIdentityApiModel.setEnabled(true);
        userIdentityApiModel.setPassword("admin");
        userIdentityApiModel.setUsername("admin2");
        userIdentityApiModel.setEmail("rxn@projext.com");
        userIdentityApiModel.setFirstName("admin");
        userIdentityApiModel.setLastName("admin");

        Optional<UserIdentity> userIdentity = userService.create(userIdentityApiModel);
        Assert.assertEquals("admin", userIdentity.get().getUsername());

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
    public void deleteUserTest()throws UserNotExistException {
        UserIdentity userIdentity = userService.delete(1L);
        Assert.assertEquals(1, userIdentity.getId().longValue());
    }

    @Test(expected = UserNotExistException.class)
    public void deleteUserNotExistException() throws UserNotExistException {
        userService.delete(9999L);
    }

    @Test(expected = UserNotExistException.class)
    public void deleteUserNullTest() throws UserNotExistException{
        userService.delete(null);

    }
}
