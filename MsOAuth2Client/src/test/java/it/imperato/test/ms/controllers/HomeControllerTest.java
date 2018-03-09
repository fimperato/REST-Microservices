package it.imperato.test.ms.controllers;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.security.Principal;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations="classpath:test.properties")
public class HomeControllerTest {

    @Autowired
    HomeController homeController;

    @Test
    public void getHomeController(){
        String res = homeController.getHomeController();
        Assert.assertEquals("Home Controller ready.",res);
    }

    @Test
    public void user(){
        Principal res = homeController.user(null);
        Assert.assertNull(res);
    }

    @Test
    public void extractActivitiesMock() {
        ResponseEntity<?> res =
                homeController.send403();
        Assert.assertEquals(HttpStatus.FORBIDDEN, res.getStatusCode());
    }

}
