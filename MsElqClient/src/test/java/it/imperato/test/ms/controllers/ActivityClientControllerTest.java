package it.imperato.test.ms.controllers;

import it.imperato.test.ms.model.restbean.Activity;
import it.imperato.test.ms.model.restbean.ActivityResponseBody;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ActivityClientControllerTest {

    @Autowired
    ActivityClientController activityClientController;

    @Test
    public void jwtActivityController(){
        String res = activityClientController.activityController();
        Assert.assertEquals("ActivityClientController (Client) ready.",res);
    }

    @Test
    public void getActivities(){
        ResponseEntity<List<Activity>> res =
                activityClientController.getActivities("1", true);
        Assert.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, res.getStatusCode());
    }

}
