package it.imperato.test.ms.controllers;

import it.imperato.test.ms.model.entities.Activity;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations="classpath:test.properties")
public class ActivityWithJwtControllerTest {

    @Autowired
    @Lazy
    ActivityWithJwtController activityControllerTest;

    @Before
    public void setup() {
    }

    @Test
    public void contextLoads() {
    }

    //@Test
    public void extractActivities(){
        String res = activityControllerTest.activityControllerTest();
        Assert.assertEquals("ActivityController (with JWT) ready.",res);
    }

    //@Test
    public void extractActivitiesMock() {
        ResponseEntity<List<Activity>>  res =
                activityControllerTest.extractActivities(null, null, "");
        Assert.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, res.getStatusCode());
    }

}
