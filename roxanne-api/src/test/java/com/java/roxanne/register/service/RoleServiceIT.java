package com.java.roxanne.register.service;


import com.heroku.roxanne.security.enumrole.RoleEnum;
import com.heroku.roxanne.security.exception.RoleAlreadyExistException;
import com.heroku.roxanne.security.exception.RoleNotExistException;
import com.heroku.roxanne.security.model.UserIdentityRole;
import com.heroku.roxanne.security.service.api.RoleService;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
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
public class RoleServiceIT {

    @Autowired
    private RoleService roleService;

    private final String UPDATED_ROLE = "UPDATED";

    private UserIdentityRole userIdentityRole;

    @Before
    public void setUp() throws RoleAlreadyExistException{
        userIdentityRole = roleService.create("ADMIN");
    }

    @After
    public void cleanUp() throws RoleNotExistException{
        roleService.delete(userIdentityRole.getId());
    }

    @Test
    public void findAll()  {
        List<UserIdentityRole> userIdentityRole = roleService.findAll();
        Assert.assertFalse(userIdentityRole.isEmpty());
    }

    @Test
    public void findByRoleByNameTest() throws RoleNotExistException{
        UserIdentityRole userIdentityRole = roleService.findByName(RoleEnum.ADMIN.name());
        Assert.assertNotNull(userIdentityRole);
    }

    @Test(expected = RoleNotExistException.class)
    public void findByRoleExceptionTest() throws RoleNotExistException{
        roleService.findByName(UPDATED_ROLE);
    }

    @Test
    public void findByRoleIdTest() throws RoleNotExistException{
        UserIdentityRole role = roleService.findById(userIdentityRole.getId());
        Assert.assertEquals(userIdentityRole.getId().longValue(), role.getId().longValue());
    }

    @Test(expected = RoleNotExistException.class)
    public void findByRoleByIdExceptionTest() throws RoleNotExistException{
        roleService.findById(22L);
    }

    @Test
    public void createRoleTest() throws RoleAlreadyExistException {
        UserIdentityRole userIdentityRole = roleService.create(RoleEnum.USER.name());
        Assert.assertNotNull(userIdentityRole);
    }

    @Test(expected = RoleAlreadyExistException.class)
    public void createRoleExceptionTest() throws RoleAlreadyExistException {
        roleService.create(RoleEnum.ADMIN.name());
    }

    @Test
    public void updateRoleTest() throws RoleNotExistException{
        UserIdentityRole role = roleService.update(userIdentityRole.getId(), UPDATED_ROLE);

        Assert.assertEquals(UPDATED_ROLE, role.getAuthority());
    }

    @Test(expected = RoleNotExistException.class)
    public void updateRoleExceptionTest() throws RoleNotExistException{
        roleService.update(99L, UPDATED_ROLE);
    }


}
