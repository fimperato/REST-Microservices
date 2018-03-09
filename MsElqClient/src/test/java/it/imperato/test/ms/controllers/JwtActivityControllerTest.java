package it.imperato.test.ms.controllers;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import it.imperato.test.ms.model.entities.JwtTokenInfo;
import it.imperato.test.ms.model.restbean.Activity;
import it.imperato.test.ms.model.restbean.ActivityRequestBody;
import it.imperato.test.ms.model.restbean.ActivityResponseBody;
import it.imperato.test.ms.utils.ConstantsApp;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.*;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.client.HttpClientErrorException;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

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
