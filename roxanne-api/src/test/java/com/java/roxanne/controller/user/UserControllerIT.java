package com.java.roxanne.controller.user;

import com.heroku.roxanne.config.BeanConfig;
import com.heroku.roxanne.controller.TestController;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.CoreMatchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@RunWith(SpringRunner.class)
@WebMvcTest(controllers = {TestController.class}, secure = false, excludeFilters = @ComponentScan.Filter(type = FilterType.REGEX, pattern = "com.heroku.roxanne"))
@ActiveProfiles({"test"})
@ContextConfiguration(classes = BeanConfig.class)
@DirtiesContext
public class UserControllerIT {

    @Autowired
    private MockMvc mockMvc;


    @Test
    public void runMe() throws Exception{
        mockMvc.perform(get("/hello")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().string(containsString("index")));
    }
}
