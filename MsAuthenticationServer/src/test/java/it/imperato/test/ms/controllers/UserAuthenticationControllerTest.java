package it.imperato.test.ms.controllers;

import it.imperato.test.ms.model.entities.User;
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
