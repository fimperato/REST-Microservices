package it.imperato.test.ms.controllers;

import it.imperato.test.ms.configurations.SpecificTestConfig;
import it.imperato.test.ms.model.entities.Activity;
import it.imperato.test.ms.model.restbean.ActivityRBean;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.mongo.MongoDataAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

@RunWith(SpringRunner.class)
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT) // , classes = { SpecificTestConfig.class }
//@ActiveProfiles({"test-profile"})
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations="classpath:test.properties")
public class ActivityControllerTest {

    @Autowired
    ActivityController activityController;

    private MockMvc mockMvc;

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(new ActivityController()).build();
    }

    @Test
    public void contextLoads() {
    }

    @Test
    public void userAuthenticationTest(){
        String res = activityController.activityControllerTest();
        Assert.assertEquals("ActivityController ready.",res);
    }

    @Test
    public void extractActivitiesMock() {
        ResponseEntity<List<ActivityRBean>> res =
                activityController.extractActivitiesMock(null,"");
        Assert.assertEquals(HttpStatus.BAD_REQUEST, res.getStatusCode());
    }

    @Test
    public void extractActivities() {
        ResponseEntity<List<Activity>> res =
                activityController.extractActivities(null,"");
        Assert.assertEquals(HttpStatus.BAD_REQUEST, res.getStatusCode());
    }

}
