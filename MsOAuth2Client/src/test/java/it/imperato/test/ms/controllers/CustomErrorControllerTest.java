package it.imperato.test.ms.controllers;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations="classpath:test.properties")
public class CustomErrorControllerTest {

    @Autowired
    CustomErrorController customErrorController;

    @Test
    public void getErrorPath(){
        String res = customErrorController.getErrorPath();
        Assert.assertEquals(CustomErrorController.PATH,res);
    }

    @Test
    public void extractActivitiesMock() {
        boolean error=false;
        try {
            customErrorController.error(null);
        } catch(Exception e) {
            error=true;
        }
        Assert.assertFalse(error);
    }

}
