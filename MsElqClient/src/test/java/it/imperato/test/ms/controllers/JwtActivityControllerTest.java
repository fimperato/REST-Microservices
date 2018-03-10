package it.imperato.test.ms.controllers;

import it.imperato.test.ms.model.restbean.ActivityResponseBody;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations="classpath:test.properties")
public class JwtActivityControllerTest {

    @Autowired
    JwtActivityController jwtActivityController;

    @Test
    public void jwtActivityController(){
        String res = jwtActivityController.jwtActivityController();
        Assert.assertEquals("JwtActivityController (Client) ready.",res);
    }

    @Test
    public void getActivities(){
        ResponseEntity<ActivityResponseBody> res =
                jwtActivityController.getActivities("1", null);
        Assert.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, res.getStatusCode());
    }

}
