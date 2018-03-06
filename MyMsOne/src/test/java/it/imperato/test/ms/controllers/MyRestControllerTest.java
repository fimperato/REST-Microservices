package it.imperato.test.ms.controllers;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class MyRestControllerTest {

    @Autowired
    MyRestController myRestController;

    @Test
    public void helloWorldTest(){
        Assert.assertEquals("Hello world!",myRestController.helloWorld());
    }

    @Test
    public void endpointOneTest(){
        ResponseEntity<MyRestController.JsonResponseBody> res = myRestController.endpointOne(null,null);
        Assert.assertEquals(HttpStatus.FORBIDDEN,res.getStatusCode());
    }

}
