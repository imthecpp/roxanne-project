package com.heroku.roxanne;

import com.heroku.roxanne.security.entity.RoleEntity;
import com.heroku.roxanne.security.entity.UserEntity;
import com.heroku.roxanne.security.exception.UserNotExistException;
import com.heroku.roxanne.security.exception.UserAlreadyExistException;
import com.heroku.roxanne.security.model.UserIdentity;
import com.heroku.roxanne.security.model.api.UserIdentityApiModel;
import com.heroku.roxanne.security.repository.RoleRepository;
import com.heroku.roxanne.security.repository.UserRepository;
import com.heroku.roxanne.security.service.api.UserService;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    private UserEntity userEntity;

    @Before
    public void setUp(){
        userEntity = new UserEntity();
        userEntity.setUsername("admin");
        userEntity.setAccountNonExpired(true);
        userEntity.setAccountNonLocked(true);
        userEntity.setEmail("roxanne@project.pl");
        userEntity.setEnabled(true);
        userEntity.setFirstName("admin");

        userEntity = userRepository.save(userEntity);

    }
    @After
    public void cleanUp(){
        userRepository.deleteById(userEntity.getId());

    }

    @Test
    public void getOneByIdTest() throws UserNotExistException{
        UserIdentity userIdentity = userService.findById(userEntity.getId());
        Assert.assertEquals(this.userEntity.getId().longValue(), userIdentity.getId().longValue());
    }

    @Test(expected = UserNotExistException.class)
    public void getOneByIdNotExistTest() throws UserNotExistException{
        userService.findById(1L);
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
    public void getAllUsersTest()  {
        List<UserIdentity> userIdentity = userService.findAll();
        Assert.assertFalse(userIdentity.isEmpty());
    }

//    @Test
//    public void getAllUsersEmptyNull() {
//        List<UserIdentity> userIdentity = userService.findAll();
//        Assert.assertTrue(userIdentity.isEmpty());
//    }


//    @Test
//    public void getAllUsersPageableTest(){
//        Pageable pageable = new PageRequest(1, 1, Sort.Direction.ASC);
//        //service
//        Assert.assertEquals(1, pageable.getPageSize());
//    }

    @Test
    public void createUserTest() throws UserAlreadyExistException{
        UserIdentityApiModel userIdentityApiModel = new UserIdentityApiModel();
        userIdentityApiModel.setAccountNonExpired(true);
        userIdentityApiModel.setAccountNonLocked(true);
        userIdentityApiModel.setCredentialsNonExpired(true);
        userIdentityApiModel.setEnabled(true);
        userIdentityApiModel.setPassword("admin");
        userIdentityApiModel.setUsername("admin2");
        userIdentityApiModel.setEmail("roxanne2@project.pl");
        userIdentityApiModel.setFirstName("admin");
        userIdentityApiModel.setLastName("admin");

        UserIdentity userIdentity = userService.create(userIdentityApiModel);
        Assert.assertEquals("admin2", userIdentity.getUsername());

    }

    @Test(expected = UserAlreadyExistException.class)
    public void userAlreadyExistExceptionTest() throws UserAlreadyExistException {
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

        UserIdentity userIdentity = userService.create(userIdentityApiModel);

    }

    @Test(expected = UserAlreadyExistException.class)
    public void createUserValidationExceptionTest() throws UserAlreadyExistException{
        UserIdentityApiModel userIdentityApiModel = new UserIdentityApiModel();
        userIdentityApiModel.setAccountNonExpired(true);
        userIdentityApiModel.setAccountNonLocked(true);
        userIdentityApiModel.setCredentialsNonExpired(true);
        userIdentityApiModel.setEnabled(true);
        userIdentityApiModel.setPassword("admin");
        userIdentityApiModel.setUsername("admin");
        userIdentityApiModel.setEmail("rxn@projext.com");
        userIdentityApiModel.setFirstName("admin");
        userIdentityApiModel.setLastName("admin");

        UserIdentity userIdentity = userService.create(userIdentityApiModel);

    }
    @Test
    public void updateUserTest() throws UserNotExistException{
        UserIdentityApiModel userIdentityApiModel = new UserIdentityApiModel();
        userIdentityApiModel.setUsername("updated");
        UserIdentity ua = userService.update(this.userEntity.getId(), userIdentityApiModel);
        Assert.assertEquals(userIdentityApiModel.getUsername(), ua.getUsername());

    }


    @Test(expected = UserNotExistException.class)
    public void updateNotExistException() throws UserNotExistException{
        UserIdentityApiModel userIdentityApiModel = new UserIdentityApiModel();
        userIdentityApiModel.setUsername("updated");
        userService.update(444L, userIdentityApiModel);

    }

//    @Test
//    public void deleteUserTest()throws UserNotExistException {
//        UserIdentity userIdentity = userService.delete(userEntity.getId());
//        Assert.assertNotNull(userIdentity);
//    }

    @Test(expected = UserNotExistException.class)
    public void deleteUserNotExistException() throws UserNotExistException {
        userService.delete(9999L);
    }

}
