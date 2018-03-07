package it.imperato.test.ms.controllers;

import io.jsonwebtoken.ExpiredJwtException;
import it.imperato.test.ms.exceptions.UserNotLoggedException;
import it.imperato.test.ms.model.entities.User;
import it.imperato.test.ms.model.pojo.UserInMem;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Optional;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserAuthenticationControllerTest {

    @Autowired
    UserAuthenticationController userAuthenticationController;

    @Test
    public void userAuthenticationTest(){
        String res = userAuthenticationController.userAuthenticationTest();
        Assert.assertEquals("userAuthentication controller ready.",res);
    }

    @Test
    public void loginUser() {
        ResponseEntity<UserAuthenticationController.UserInMemJwtInfoResponseBody> res =
                userAuthenticationController.loginUser(null,
                        new User("id","user","pass", "permission"));
        Assert.assertEquals(HttpStatus.FORBIDDEN, res.getStatusCode());
    }

}
